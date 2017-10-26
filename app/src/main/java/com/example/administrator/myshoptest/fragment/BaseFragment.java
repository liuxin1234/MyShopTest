package com.example.administrator.myshoptest.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    public String msg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initView();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
//        setToastShow(msg);
    }

    /***
     * 强制子类重写，实现子类特有的ui
     * @return
     */
    protected abstract View initView();


    /***
     * 当孩子需要初始化数据，或者联网请求绑定数据，重写这个方法
     */
    protected  void initData(){

    }

    public void setToastShow(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

}
