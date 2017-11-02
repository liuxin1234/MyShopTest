package com.example.administrator.myshoptest.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.adapter.HWAdapter;
import com.example.administrator.myshoptest.httpUtils.Contants;
import com.example.administrator.myshoptest.widget.CnToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by
 * 项目名称：com.example.administrator.myshoptest.activity
 * 项目日期：2017/10/26
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class WareListActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.txt_summary)
    TextView txtSummary;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id.toolbar)
    CnToolbar toolbar;

    private static final String TAG = "WareListActivity";

    public static final int TAG_DEFAULT=0;
    public static final int TAG_SALE=1;
    public static final int TAG_PRICE=2;

    public static final int ACTION_LIST=1;
    public static final int ACTION_GIRD=2;

    private int orderBy = 0;
    private long campaignId = 0;

    private HWAdapter mWaresAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warelist);
        ButterKnife.bind(this);
        initToolBar();
        campaignId=getIntent().getLongExtra(Contants.COMPAINGAIN_ID,0);
        initTab();
        getData();
    }

    private void initToolBar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WareListActivity.this.finish();
            }
        });
        toolbar.setRightButtonIcon(R.drawable.icon_grid_32);
        toolbar.getRightButton().setTag(ACTION_LIST);
        toolbar.setRightButtonOnClickListener(this);
    }

    private void initTab() {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText("默认");
        tab.setTag(TAG_DEFAULT);
        tabLayout.addTab(tab);

        tab= tabLayout.newTab();
        tab.setText("价格");
        tab.setTag(TAG_PRICE);
        tabLayout.addTab(tab);

        tab= tabLayout.newTab();
        tab.setText("销量");
        tab.setTag(TAG_SALE);
        tabLayout.addTab(tab);

        tabLayout.setOnTabSelectedListener(this);
    }


    private void getData() {
//        txtSummary.setText("共有"+totalCount+"件商品");
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        orderBy = (int) tab.getTag();

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        int action = (int) v.getTag();

        if (ACTION_LIST == action){
            toolbar.setRightButtonIcon(R.drawable.icon_list_32);
            toolbar.getRightButton().setTag(ACTION_GIRD);
            mWaresAdapter.resetLayout(R.layout.template_grid_wares);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            recyclerView.setAdapter(mWaresAdapter);
        }else if (ACTION_GIRD == action){
            toolbar.setRightButtonIcon(R.drawable.icon_list_32);
            toolbar.getRightButton().setTag(ACTION_LIST);
            mWaresAdapter.resetLayout(R.layout.template_hot_wares);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mWaresAdapter);
        }
    }
}
