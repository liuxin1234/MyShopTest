package com.example.administrator.myshoptest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.activity.Base.BaseActivity;
import com.example.administrator.myshoptest.utils.ToastUtils;
import com.example.administrator.myshoptest.widget.ClearEditText;
import com.example.administrator.myshoptest.widget.ToolbarHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author 75095
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.etxt_phone)
    ClearEditText mEtxtPhone;
    @BindView(R.id.etxt_pwd)
    ClearEditText mEtxtPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.txt_toReg)
    TextView mTxtToReg;

    private String phone;
    private String password;


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("登录");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initView() {
        initGetText();
    }


    private void initGetText() {
        phone = mEtxtPhone.getText().toString();
        password = mEtxtPwd.getText().toString();
    }



    private void login(){
        if (phone.isEmpty()){
            ToastUtils.show(this,"手机号不能为空!");
        }else if (password.isEmpty()){
            ToastUtils.show(this,"密码不能为空!");
        }else {
            queryUserData();
        }
    }

    private void queryUserData() {


    }


    @OnClick({R.id.btn_login, R.id.txt_toReg})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.txt_toReg:
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
