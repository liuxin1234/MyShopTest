package com.example.administrator.myshoptest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.utils.ToastUtils;
import com.example.administrator.myshoptest.widget.ClearEditText;
import com.example.administrator.myshoptest.widget.CnToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_toolbar)
    CnToolbar mLoginToolbar;
    @BindView(R.id.etxt_phone)
    ClearEditText mEtxtPhone;
    @BindView(R.id.etxt_pwd)
    ClearEditText mEtxtPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.txt_toReg)
    TextView mTxtToReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        showToolbar();
    }

    private void showToolbar() {
        mLoginToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.etxt_phone, R.id.etxt_pwd, R.id.btn_login, R.id.txt_toReg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etxt_phone:
                break;
            case R.id.etxt_pwd:
                break;
            case R.id.btn_login:
                ToastUtils.show(this,"点击登录");
                break;
            case R.id.txt_toReg:
                ToastUtils.show(this,"点击注册");


                break;
        }
    }

}
