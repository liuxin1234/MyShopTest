package com.example.administrator.myshoptest.bean;


import java.io.Serializable;

/**
 * Created by
 * 项目名称：com.example.administrator.myshoptest.bean
 * 项目日期：2017/10/31
 * 作者：liux
 * 功能：
 */

public class User implements Serializable{
    private String userName;
    private String phone;
    private String passWord;
    private String headUrl;
    private String consignee; //收件人
    private String address;   //地址
    private String addDetial; //详细地址

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddDetial() {
        return addDetial;
    }

    public void setAddDetial(String addDetial) {
        this.addDetial = addDetial;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", passWord='" + passWord + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", consignee='" + consignee + '\'' +
                ", address='" + address + '\'' +
                ", addDetial='" + addDetial + '\'' +
                '}';
    }
}
