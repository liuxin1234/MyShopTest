package com.example.administrator.myshoptest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/14.
 */

public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Wares> mWaresList;

    public HotWaresAdapter(Context context, List<Wares> waresList) {
        mContext = context;
        mWaresList = waresList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.template_hot_wares, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wares wares = mWaresList.get(position);
        holder.mDraweeView.setImageURI(wares.getImgUrl());
        holder.mTextTitle.setText(wares.getName());
        holder.mTextPrice.setText("￥"+wares.getPrice());
    }

    public void clearData(){
        mWaresList.clear();
        notifyItemRangeRemoved(0,mWaresList.size());
    }

    public void addData(List<Wares> datas){
        addData(0,datas);
    }

    public void addData(int position,List<Wares> datas){
        if (datas != null && datas.size()> 0 ){
            mWaresList.addAll(datas);
            notifyItemRangeChanged(position,mWaresList.size());
        }
    }

    public List<Wares> getDatas(){
        return mWaresList;
    }

    @Override
    public int getItemCount() {
        if (mWaresList != null && mWaresList.size() > 0) {
            return mWaresList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.drawee_view)
        SimpleDraweeView mDraweeView;
        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_price)
        TextView mTextPrice;
        @BindView(R.id.btn_add)
        Button mBtnAdd;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
