package com.example.administrator.myshoptest.adapter;

import android.content.Context;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.adapter.baseAdapter.BaseViewHolder;
import com.example.administrator.myshoptest.adapter.baseAdapter.SimpleAdapter;
import com.example.administrator.myshoptest.bean.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class CategoryAdapter extends SimpleAdapter<Category> {



    public CategoryAdapter(Context context, List datas) {
        super(context, datas, R.layout.template_single_text);
    }


    @Override
    public void bindData(BaseViewHolder holder, Category category) {
        holder.getTextView(R.id.textView).setText(category.getName());
    }
}
