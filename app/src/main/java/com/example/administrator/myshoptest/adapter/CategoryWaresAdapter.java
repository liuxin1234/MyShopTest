package com.example.administrator.myshoptest.adapter;

import android.content.Context;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.adapter.baseAdapter.BaseViewHolder;
import com.example.administrator.myshoptest.adapter.baseAdapter.SimpleAdapter;
import com.example.administrator.myshoptest.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class CategoryWaresAdapter extends SimpleAdapter<Wares> {

    public CategoryWaresAdapter(Context context, List<Wares> datas) {
        super(context, datas, R.layout.template_grid_wares);
    }

    @Override
    public void bindData(BaseViewHolder holder, Wares wares) {
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        draweeView.setImageURI(wares.getImgUrl());
        holder.getTextView(R.id.text_title).setText(wares.getName());
        holder.getTextView(R.id.text_price).setText("￥"+wares.getPrice());
    }
}
