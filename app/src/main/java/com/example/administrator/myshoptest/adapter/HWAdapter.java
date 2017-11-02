package com.example.administrator.myshoptest.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.adapter.baseAdapter.BaseViewHolder;
import com.example.administrator.myshoptest.adapter.baseAdapter.SimpleAdapter;
import com.example.administrator.myshoptest.bean.Wares;
import com.example.administrator.myshoptest.utils.CartProvider;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class HWAdapter extends SimpleAdapter<Wares> {

    private OnButtonClickListener mListener;

    private CartProvider mCartProvider;

    public HWAdapter(Context context, List<Wares> datas) {
        super(context, datas, R.layout.template_hot_wares);
        mCartProvider = new CartProvider(mContext);
    }

    public void resetLayout(int layoutId){
        this.mLayoutResId = layoutId;
        notifyItemRangeChanged(0,getDatas().size());
    }

    @Override
    public void bindData(final BaseViewHolder holder, final Wares wares) {

        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        draweeView.setImageURI(wares.getImgUrl());
        holder.getTextView(R.id.text_title).setText(wares.getName());
        holder.getTextView(R.id.text_price).setText("￥"+wares.getPrice());
        holder.getButton(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnButtonClickListener(holder.itemView,wares);
            }
        });
    }


    public interface OnButtonClickListener{
        void OnButtonClickListener(View view, Wares wares);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){
        this.mListener = listener;
    }


}
