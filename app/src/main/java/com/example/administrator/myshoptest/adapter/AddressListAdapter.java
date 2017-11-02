package com.example.administrator.myshoptest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.adapter.baseAdapter.BaseAdapter;
import com.example.administrator.myshoptest.adapter.baseAdapter.BaseViewHolder;
import com.example.administrator.myshoptest.adapter.baseAdapter.SimpleAdapter;
import com.example.administrator.myshoptest.bean.User;
import com.example.administrator.myshoptest.utils.ToastUtils;

import java.util.List;

/**
 * Created by
 * 项目名称：com.example.administrator.myshoptest.adapter
 * 项目日期：2017/11/1
 * 作者：liux
 * 功能：
 */

public class AddressListAdapter extends SimpleAdapter<User> implements BaseAdapter.OnItemClickListener{


    public AddressListAdapter(Context context, List<User> datas) {
        super(context, datas, R.layout.item_layout_address);
    }

    @Override
    public void OnItemClick(View view, int position) {

    }

    @Override
    public void bindData(BaseViewHolder holder, User user) {
        holder.getTextView(R.id.tv_consignee).setText(user.getConsignee());
        holder.getTextView(R.id.tv_phone).setText(user.getPhone());
        holder.getTextView(R.id.tv_address).setText(user.getAddress()+user.getAddDetial());
//        holder.getTextView(R.id.radio_address)
        holder.getTextView(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(mContext,"点击了编辑");
            }
        });

        holder.getTextView(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(mContext,"点击了删除");
            }
        });
    }
}
