package com.example.administrator.myshoptest.eventbus;

/**
 * Created by
 * 项目名称：com.example.administrator.myshoptest.eventbus
 * 项目日期：2017/10/16
 * 作者：liux
 * 功能：
 */

public class DataSynEvent {

    private int count;

    public DataSynEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
