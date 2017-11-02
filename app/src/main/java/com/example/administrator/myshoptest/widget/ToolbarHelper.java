package com.example.administrator.myshoptest.widget;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myshoptest.R;


/**
 * 项目：My_Ali_IM
 * 作者：Administrator
 * 时间：2017/7/26
 * 功能：
 */

public class ToolbarHelper {
    private Toolbar mToolbar;

    public ToolbarHelper(Toolbar toolbar) {
        this.mToolbar = toolbar;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setTitle(String title) {
        TextView titleTV = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        titleTV.setText(title);
    }

    public void setMenuTitle(String menuTitle, View.OnClickListener listener) {
        TextView menuTitleTV = (TextView) mToolbar.findViewById(R.id.toolbar_menu_title);
        menuTitleTV.setText(menuTitle);
        menuTitleTV.setOnClickListener(listener);
    }
}