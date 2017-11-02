package com.example.administrator.myshoptest.utils;

import android.content.Context;
import android.util.SparseArray;

import com.example.administrator.myshoptest.bean.ShoppingCart;
import com.example.administrator.myshoptest.bean.Wares;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/2/27.
 * 存储，删除，更新，购物车的数据。
 *  将数据存储到本地
 */

public class CartProvider {

    private static final String CART_JSON = "cart_json";

    /**
     * 这个是Android提供的工具类，相当于HashMap,但比hashMap更高效。
     */
    private SparseArray<ShoppingCart> datas = null;

    private Context mContext;

    public CartProvider(Context context) {
        this.datas = new SparseArray<>(10);
        mContext = context;
        listToSparse();
    }


    public void put(ShoppingCart cart){
        ShoppingCart temp = datas.get(cart.getId().intValue());
        if (temp != null){
            temp.setCount(temp.getCount()+1);
        }else {
            temp =cart;
            temp.setCount(1);
        }
        datas.put(cart.getId().intValue(),temp);
        commit();

    }


    public void updata(ShoppingCart cart){
        datas.put(cart.getId().intValue(),cart);
        commit();
    }



    public void delete(ShoppingCart cart){
        datas.delete(cart.getId().intValue());
        commit();

    }

    public List<ShoppingCart> getAll(){

        return getDataFromLocal();
    }

    /**
     * 提交数据
     */
    public void commit(){
        List<ShoppingCart> carts = sparseToList();
        PreferencesUtils.putString(mContext,CART_JSON,JSONUtil.toJSON(carts));
    }


    private List<ShoppingCart> sparseToList(){
        int size = datas.size();
        List<ShoppingCart> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(datas.valueAt(i));
        }
        return list;
    }

    private void listToSparse(){
        List<ShoppingCart> carts = getDataFromLocal();
        if (carts != null && carts.size()>0){
            for (ShoppingCart cart:carts) {
                datas.put(cart.getId().intValue(),cart);
            }
        }
    }

    /**
     * 取数据
     * @return
     */
    public List<ShoppingCart> getDataFromLocal(){
        String json = PreferencesUtils.getString(mContext, CART_JSON);
        List<ShoppingCart> carts = null;
        if (json != null ){
           carts = JSONUtil.fromJson(json,new TypeToken<List<ShoppingCart>>(){}.getType());
        }

        return carts;
    }


    public ShoppingCart convertData(Wares item){

        ShoppingCart cart = new ShoppingCart();

        cart.setId(item.getId());
        cart.setDescription(item.getDescription());
        cart.setImgUrl(item.getImgUrl());
        cart.setName(item.getName());
        cart.setPrice(item.getPrice());

        return cart;
    }

    /**
     * 代码重构
     */
    public void put(Wares wares) {
        ShoppingCart cart = convertData(wares);
        put(cart);
    }
}
