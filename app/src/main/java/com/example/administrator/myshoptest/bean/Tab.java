package com.example.administrator.myshoptest.bean;

/**
 * Created by Administrator on 2017/1/13.
 */

public class Tab {
    private int title;
    private int icon;
    private boolean message;
    private Class fragment;

    public Tab() {

    }

    public Tab(Class fragment,int title, int icon, boolean message) {
        this.title = title;
        this.icon = icon;
        this.message = message;
        this.fragment = fragment;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
