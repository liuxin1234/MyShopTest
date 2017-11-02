package com.example.administrator.myshoptest.activity.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.widget.ToolbarHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/2/24.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mUnbinder = ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            // 默认不显示原生标题
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            initToolbar(new ToolbarHelper(toolbar));
        }
        initView();
    }


    protected abstract int getContentView();

    protected abstract void initToolbar(ToolbarHelper toolbarHelper);

    /***
     * 初始化view
     */
    public abstract void  initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
