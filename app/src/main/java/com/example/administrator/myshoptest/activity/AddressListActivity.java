package com.example.administrator.myshoptest.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.activity.Base.BaseActivity;
import com.example.administrator.myshoptest.adapter.AddressListAdapter;
import com.example.administrator.myshoptest.bean.User;
import com.example.administrator.myshoptest.widget.ToolbarHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by
 * 项目名称：com.example.administrator.myshoptest.activity
 * 项目日期：2017/11/1
 * 作者：liux
 * 功能：
 *
 * @author 75095
 */

public class AddressListActivity extends BaseActivity {


    @BindView(R.id.rv_address)
    RecyclerView rvAddress;

    private AddressListAdapter mAdapter;
    private List<User> mUserList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_list_address;
    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("我的地址");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarHelper.setMenuTitle("添加",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListActivity.this,AddressActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void initView() {

        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new AddressListAdapter(this,mUserList);
        rvAddress.setLayoutManager(new LinearLayoutManager(this));
        rvAddress.setAdapter(mAdapter);
    }

    /**
     * 设置跳转  接受返回数据
     * @param requestCode
     *              请求码
     * @param resultCode
     *              返回码
     * @param data
     *              返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  如果请求码与返回码等于预期设置的值  则进行后续操作
        if (requestCode == 1 && resultCode == 2){
            // 获取返回的数据
            Serializable extra = data.getSerializableExtra("data");
            User user = (User) extra;
            mUserList.clear();
            mUserList.add(user);
            mAdapter.notifyDataSetChanged();
        }
    }
}
