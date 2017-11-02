package com.example.administrator.myshoptest.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.adapter.baseAdapter.BaseAdapter;
import com.example.administrator.myshoptest.adapter.baseAdapter.BaseViewHolder;
import com.example.administrator.myshoptest.adapter.baseAdapter.SimpleAdapter;
import com.example.administrator.myshoptest.bean.ShoppingCart;
import com.example.administrator.myshoptest.utils.CartProvider;
import com.example.administrator.myshoptest.widget.NumberAddSubView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */

public class CartAdapter extends SimpleAdapter<ShoppingCart> implements BaseAdapter.OnItemClickListener {

    public static final String TAG="CartAdapter";


    private CheckBox mCheckBox;
    private TextView mTextView;

    private CartProvider cartProvider;



    public CartAdapter(Context context, List<ShoppingCart> datas,final CheckBox checkBox, TextView textView) {
        super(context, datas, R.layout.template_cart);
        setCheckBox(checkBox);
        setTextView(textView);
        cartProvider = new CartProvider(context);
        setOnItemClickListener(this);
        showTotalPrice();

    }



    @Override
    public void bindData(BaseViewHolder holder, final ShoppingCart cart) {
        holder.getTextView(R.id.text_title).setText(cart.getName());
        holder.getTextView(R.id.text_price).setText("￥"+cart.getPrice());
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        simpleDraweeView.setImageURI(cart.getImgUrl());

        Log.e(TAG, "bindData: "+cart.isChecked());
        CheckBox checkBox = (CheckBox) holder.getView(R.id.checkbox);
        checkBox.setChecked(cart.isChecked());

        NumberAddSubView numberAddSubView = (NumberAddSubView) holder.getView(R.id.num_control);
        numberAddSubView.setValue(cart.getCount());
        numberAddSubView.setOnButtonClickListener(new NumberAddSubView.OnButtonClickListener() {
            @Override
            public void onButtonAddClick(View view, int value) {
                cart.setCount(value);
                cartProvider.updata(cart);
                showTotalPrice();
            }

            @Override
            public void onButtonSubClick(View view, int value) {
                cart.setCount(value);
                cartProvider.updata(cart);
                showTotalPrice();
            }
        });

    }

    public void showTotalPrice() {
        float total = getTotalPrice();
        mTextView.setText(Html.fromHtml("合计 ￥<span style='color:#eb4f38'>" + total + "</span>"), TextView.BufferType.SPANNABLE);

    }

    private float getTotalPrice() {

        float sum = 0;
        if (!isNull()){
            return sum;
        }

        for (ShoppingCart cart :
                mDatas) {
            if (cart.isChecked()){
                sum += cart.getCount()*cart.getPrice();
            }
        }
        return sum;
    }

    private boolean isNull() {
        return (mDatas !=null && mDatas.size()>0);
    }


    private void setTextView(TextView textView) {
        this.mTextView = textView;

    }

    private void setCheckBox(final CheckBox checkBox) {
        this.mCheckBox = checkBox;
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAll_None(mCheckBox.isChecked());
                showTotalPrice();
            }
        });
    }

    @Override
    public void OnItemClick(View view, int position) {
        ShoppingCart cart = getItem(position);
        cart.setIsChecked(!cart.isChecked());
        notifyItemChanged(position);
        Log.e(TAG, "OnItemClick: "+cart.isChecked());
        checkListener();
        showTotalPrice();

    }

    private void checkListener() {
        int count = 0;
        int checkNum = 0;
        if (mDatas != null){
            count = mDatas.size();
            Log.e(TAG, "checkListener: 1111111");
            for (ShoppingCart cart : mDatas){
                if (!cart.isChecked()){
                    mCheckBox.setChecked(false);
                    break;
                }else {
                    checkNum = checkNum + 1;
                }
            }
            if (count == checkNum){
                mCheckBox.setChecked(true);
            }
        }
    }

    /**
     * 全选/全不选
     * @param isChecked
     */
    public void checkAll_None(boolean isChecked){
        if (!isNull()){
            return;
        }
        int i = 0;
        for (ShoppingCart cart: mDatas) {
            cart.setIsChecked(isChecked);
            notifyItemChanged(i);
            i++;
        }
    }

    /**
     * 删除商品
     */

    public void delcart(){
        if (!isNull()){
            return;
        }
        for (Iterator iterator = mDatas.iterator();iterator.hasNext();){
            ShoppingCart cart = (ShoppingCart) iterator.next();
            if (cart.isChecked()){
                int position = mDatas.indexOf(cart);
                cartProvider.delete(cart);
                iterator.remove();
                notifyItemRemoved(position);
            }

        }
    }



}
