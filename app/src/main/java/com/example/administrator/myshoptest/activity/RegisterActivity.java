package com.example.administrator.myshoptest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.activity.Base.BaseActivity;
import com.example.administrator.myshoptest.bean.User;
import com.example.administrator.myshoptest.utils.ToastUtils;
import com.example.administrator.myshoptest.widget.ClearEditText;
import com.example.administrator.myshoptest.widget.CnToolbar;
import com.example.administrator.myshoptest.widget.ToolbarHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author 75095
 *         注册界面
 */
public class RegisterActivity extends BaseActivity {


    @BindView(R.id.etxt_userName)
    ClearEditText etxtUserName;
    @BindView(R.id.etxt_phone)
    ClearEditText etxtPhone;
    @BindView(R.id.etxt_pwd)
    ClearEditText etxtPwd;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private String userName;
    private String phone;
    private String password;



    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("注册");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initView() {
        initGetText();
    }

    private void initGetText() {
        userName = etxtUserName.getText().toString();
        phone = etxtPhone.getText().toString();
        password = etxtPwd.getText().toString();
    }

    private void registerInfo() {

    }

    private void register(){
        if (userName.isEmpty()){
            ToastUtils.show(this,"用户名不能为空!");
        }else if (phone.isEmpty()){
            ToastUtils.show(this,"手机号不能为空!");
        }else if (password.isEmpty()){
            ToastUtils.show(this,"密码不能为空!");
        }else {
            registerInfo();
        }

    }


    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        register();
    }
}
