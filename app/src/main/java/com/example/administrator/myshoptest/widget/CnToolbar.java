package com.example.administrator.myshoptest.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myshoptest.R;

/**
 * Created by Administrator on 2017/2/23.
 */

public class CnToolbar extends Toolbar {

    EditText mToolbarSearchview;
    TextView mToolbarTitle;
    Button mToolbarRightButton;
    Button mToolbarLeftButton;
    private LayoutInflater mLayoutInflater;
    private View mView;
    private Drawable mLeftButtonIcon;


    public CnToolbar(Context context) {
        this(context, null);
    }

    public CnToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CnToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
        //设置边距
        setContentInsetsRelative(10, 10);

        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.CnToolbar, defStyleAttr, 0);
            //如果右边是图片
            final Drawable navIcon = a.getDrawable(R.styleable.CnToolbar_rightButtonIcon);
            if (navIcon != null) {
                //setNavigationIcon(navIcon);
                setRightButtonIcon(navIcon);
            }

            //如果右边是文字
            CharSequence rightButtonText = a.getText(R.styleable.CnToolbar_rightButtonIcon);
            if (rightButtonText != null) {
                setRightButtonText(rightButtonText);
            }

            boolean isShowSearchView = a.getBoolean(R.styleable.CnToolbar_isShowSearchView, false);

            if (isShowSearchView) {
                showTitleView();
                hideTitleView();
            }


            final Drawable leftIcon = a.getDrawable(R.styleable.CnToolbar_leftButtonIcon);
            if (leftIcon != null) {
                setLeftButtonIcon(leftIcon);
            }


            //读完以后进行关闭
            a.recycle();
        }

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setRightButtonIcon(Drawable icon) {
        if (mToolbarRightButton != null){
            mToolbarRightButton.setBackground(icon);
            mToolbarRightButton.setVisibility(VISIBLE);
        }

    }


    public void setRightButtonText(CharSequence text){
        mToolbarRightButton.setText(text);
        mToolbarRightButton.setVisibility(VISIBLE);
    }

    private void initView() {

        if (mView == null) {
            mLayoutInflater = LayoutInflater.from(getContext());
            mView = mLayoutInflater.inflate(R.layout.toolbar, null);
            mToolbarTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mToolbarSearchview = (EditText) mView.findViewById(R.id.toolbar_searchview);
            mToolbarRightButton = (Button) mView.findViewById(R.id.toolbar_rightButton);
            mToolbarLeftButton = (Button) mView.findViewById(R.id.toolbar_leftButton);

            ViewGroup.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);
        }
    }



    public void setRightButtonOnclickListener(OnClickListener listener){
        mToolbarRightButton.setOnClickListener(listener);
    }

    public void setLeftButtonOnClickListener(OnClickListener listener){
        mToolbarLeftButton.setOnClickListener(listener);
    }

    public Button getRightButton(){
        return this.mToolbarRightButton;
    }

    public Button getLeftButton(){
        return this.mToolbarLeftButton;
    }

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
            showTitleView();
        }

    }

    public void showSearchView() {
        if (mToolbarSearchview != null)
            mToolbarSearchview.setVisibility(VISIBLE);
    }


    public void hideSearchView() {
        if (mToolbarSearchview != null)
            mToolbarSearchview.setVisibility(GONE);
    }

    public void showTitleView() {
        if (mToolbarTitle != null)
            mToolbarTitle.setVisibility(VISIBLE);
    }

    public void hideTitleView() {
        if (mToolbarTitle != null)
            mToolbarTitle.setVisibility(GONE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setLeftButtonIcon(Drawable leftButtonIcon) {
        if (mToolbarLeftButton != null){
            mToolbarLeftButton.setBackground(leftButtonIcon);
            mToolbarLeftButton.setVisibility(VISIBLE);
        }
    }
}
