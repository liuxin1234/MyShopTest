package com.example.administrator.myshoptest.adapter.baseAdapter;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder> {

    public SimpleAdapter(Context context, List<T> datas, int layoutResId) {
        super(context, datas, layoutResId);
    }
}
