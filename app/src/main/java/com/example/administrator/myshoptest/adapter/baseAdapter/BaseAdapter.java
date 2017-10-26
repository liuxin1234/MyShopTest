package com.example.administrator.myshoptest.adapter.baseAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public abstract class BaseAdapter<T,H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mDatas;
    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected int mLayoutResId;
    protected OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public BaseAdapter(Context context, List<T> datas, int layoutResId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutResId = layoutResId;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(mLayoutResId,parent,false);
        return new BaseViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        T t = getItem(position);
        bindData((H) holder,t);

    }

    public void clearData(){
        mDatas.clear();
        notifyItemRangeRemoved(0,mDatas.size());
    }

    public void addData(List<T> datas){
        addData(0,datas);
    }

    public void addData(int position,List<T> datas){
        if (datas != null && datas.size()> 0 ){
            mDatas.addAll(datas);
            notifyItemRangeChanged(position,mDatas.size());
        }
    }

    public List<T> getDatas(){
        return mDatas;
    }

    @Override
    public int getItemCount() {
        if (mDatas ==null || mDatas.size()<=0){
            return 0;
        }

        return mDatas.size();
    }

    public T getItem(int position){
        if (position >= mDatas.size()){
            return null;
        }
        return mDatas.get(position);
    }

    public abstract void bindData(H holder, T t);
}
