package com.example.administrator.myshoptest.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myshoptest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/27.
 */

public class NumberAddSubView extends LinearLayout {
    public static final String TAG = "NumberAddSubView";
    public static final int DEFUALT_MAX = 1000;
    @BindView(R.id.btn_sub)
    Button mBtnSub;
    @BindView(R.id.etxt_num)
    TextView mEtxtNum;
    @BindView(R.id.btn_add)
    Button mBtnAdd;

    private LayoutInflater mLayoutInflater;

    private OnButtonClickListener mListener;

    private int value;
    private int minValue;
    private int maxValue = DEFUALT_MAX;


    public NumberAddSubView(Context context) {
        this(context, null);
    }

    public NumberAddSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutInflater = LayoutInflater.from(context);
        initView();
        setAttrs(attrs,defStyleAttr);
    }

    private void setAttrs(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null){
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.NumberAddSubView, defStyleAttr, 0);
            int val = a.getInt(R.styleable.NumberAddSubView_value,0);
            setValue(val);

            int maxVal = a.getInt(R.styleable.NumberAddSubView_maxValue,0);
            if (maxVal != 0){
                setMaxValue(maxVal);
            }
            int minVal = a.getInt(R.styleable.NumberAddSubView_minValue,0);
            setMinValue(minVal);

            Drawable etBackground = a.getDrawable(R.styleable.NumberAddSubView_editBackground);
            if (etBackground != null){
                setEditTextBackground(etBackground);
            }

            Drawable buttonAddBackground = a.getDrawable(R.styleable.NumberAddSubView_buttonAddBackgroud);
            if (buttonAddBackground != null){
                setButtonAddBackground(buttonAddBackground);
            }

            Drawable buttonSubBackground = a.getDrawable(R.styleable.NumberAddSubView_buttonSubBackgroud);
            if (buttonSubBackground != null){
                setButtonSubBackground(buttonSubBackground);
            }

            a.recycle();
        }
    }

    private View initView() {
        View view = mLayoutInflater.inflate(R.layout.widet_num_add_sub, this, true);
        ButterKnife.bind(this);
        return view;
    }

    @OnClick({R.id.btn_sub, R.id.btn_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sub:
                numSub();
                if (mListener != null){
                    mListener.onButtonAddClick(view,this.value);
                }
                break;
            case R.id.btn_add:
                numAdd();
                if (mListener != null){
                    mListener.onButtonSubClick(view,this.value);
                }
                break;
        }
    }


    private void numAdd(){
        getValue();
        if (this.value <= maxValue){
            this.value = this.value+1;
        }

        mEtxtNum.setText(value+"");
    }


    private void numSub(){

        getValue();
        if (this.value > minValue){
            this.value = this.value-1;
        }
        mEtxtNum.setText(value+"");


    }

    public int getValue(){
        String value = mEtxtNum.getText().toString();
        if (value != null && !"".equals(value)){
            this.value = Integer.parseInt(value);
        }
        return this.value;
    }


    public void setValue(int value){
        mEtxtNum.setText(value+"");
        this.value = value;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setEditTextBackground(int drawableId){
        setEditTextBackground(getResources().getDrawable(drawableId));
    }

    private void setEditTextBackground(Drawable drawable) {
        mEtxtNum.setBackgroundDrawable(drawable);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setButtonAddBackground(Drawable backgroud){
        this.mBtnAdd.setBackground(backgroud);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setButtonSubBackground(Drawable backgroud){
        this.mBtnSub.setBackground(backgroud);
    }

    public interface OnButtonClickListener{
        void onButtonAddClick(View view, int value);
        void onButtonSubClick(View view, int value);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){
        this.mListener = listener;
    }
}
