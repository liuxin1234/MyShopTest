package com.example.administrator.myshoptest.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/22.
 */

public class Category implements Serializable {


    /**
     * id : 1
     * name : 热门推荐
     * sort : 1
     */

    private int id;
    private String name;
    private int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
