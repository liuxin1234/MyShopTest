package com.example.administrator.myshoptest.activity;

import android.os.Bundle;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.activity.Base.BaseActivity;

/**
 * Created by Administrator on 2017/2/27.
 */

public class TestActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_cart);
    }
}
