package com.example.administrator.myshoptest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.myshoptest.bean.Tab;
import com.example.administrator.myshoptest.eventbus.DataSynEvent;
import com.example.administrator.myshoptest.fragment.CartFragment;
import com.example.administrator.myshoptest.fragment.CategoryFragment;
import com.example.administrator.myshoptest.fragment.HomeFragment;
import com.example.administrator.myshoptest.fragment.HotFragment;
import com.example.administrator.myshoptest.fragment.MineFragment;
import com.example.administrator.myshoptest.widget.FragmentTabHost;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "MainActivity";

    private LayoutInflater mInflater;
    private FragmentTabHost mTabhost;
    private CartFragment mCartFragment;

    private List<Tab> mTabs = new ArrayList<>();

    TextView unread;

    private int tagFragment;

    public int getTagFragment() {
        return tagFragment;
    }

    public void setTagFragment(int tagFragment) {
        this.tagFragment = tagFragment;
        Log.e(TAG, "onResume: "+mTabhost );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initTab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tagFragment == 1){
            Log.e(TAG, "onResume: "+mTabhost );
            mTabhost.setCurrentTab(1);
        }
    }

    private void initTab() {
        Tab tab_home = new Tab(HomeFragment.class, R.string.home, R.drawable.selector_icon_home,false);
        Tab tab_hot = new Tab(HotFragment.class, R.string.hot, R.drawable.selector_icon_hot,false);
        Tab tab_category = new Tab(CategoryFragment.class, R.string.catagory, R.drawable.selector_icon_category,true);
        Tab tab_cart = new Tab(CartFragment.class, R.string.cart, R.drawable.selector_icon_cart,false);
        Tab tab_mine = new Tab(MineFragment.class, R.string.mine, R.drawable.selector_icon_mine,false);

        mTabs.add(tab_home);
        mTabs.add(tab_hot);
        mTabs.add(tab_category);
        mTabs.add(tab_cart);
        mTabs.add(tab_mine);

        mInflater = LayoutInflater.from(this);

        mTabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabhost.addTab(tabSpec, tab.getFragment(), null);
        }


        mTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.e(TAG, "onTabChanged: "+tabId );

                if (tabId.equals(getString(R.string.cart))){
                    refData();
                }
            }
        });


        //去除分割线
        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabhost.setCurrentTab(0);//选中第一个
    }


    private View buildIndicator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView tv = (TextView) view.findViewById(R.id.txt_indicator);

        /**
         * 如果这个未读数字放在这里findViewById话，需要在各个约束条件下设置全局的TextView对象
         */
//        if (tab == mTabs.get(1)){
//            unread = (TextView)  view.findViewById(R.id.unread);
//        }

        img.setBackgroundResource(tab.getIcon());
        tv.setText(tab.getTitle());


        return view;
    }


    private void refData(){

        if(mCartFragment == null){

            Fragment fragment =  getSupportFragmentManager().findFragmentByTag(getString(R.string.cart));
            if(fragment !=null){
                mCartFragment= (CartFragment) fragment;
                mCartFragment.refData();
            }
        }
        else{
            mCartFragment.refData();

        }
    }

    /**
     *    NAIN UI主线程
     *   BACKGROUND 后台线程
     *   POSTING 和发布者处在同一个线程
     *   ASYNC 异步线程
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEventMainThread(DataSynEvent event){
        mTabhost.setCurrentTab(event.getCount());

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
