package com.example.administrator.myshoptest.httpUtils;

/**
 * Created by Administrator on 2017/1/10.
 * 这算是所有实体的一个基类，data 可以为任何数据类型。
 */
public class HttpResult<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
