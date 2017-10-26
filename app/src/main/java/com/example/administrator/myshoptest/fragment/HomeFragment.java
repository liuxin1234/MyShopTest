package com.example.administrator.myshoptest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.adapter.HomeCatgoryAdapter;
import com.example.administrator.myshoptest.adapter.decoration.CardViewtemDecortion;
import com.example.administrator.myshoptest.bean.BannerBean;
import com.example.administrator.myshoptest.bean.Campaign;
import com.example.administrator.myshoptest.bean.HomeCampaign;
import com.example.administrator.myshoptest.httpUtils.Api;
import com.example.administrator.myshoptest.httpUtils.ApiService;
import com.example.administrator.myshoptest.utils.ToastUtils;
import com.example.administrator.myshoptest.widget.CnToolbar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 主页面
 * Created by Ivan on 15/9/25.
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    @BindView(R.id.benner)
    Banner mBenner;
    @BindView(R.id.recycleview)
    RecyclerView mRecycleview;
    @BindView(R.id.home_toolbar)
    CnToolbar mHomeToolbar;

    private ApiService apiService = Api.getDefault();
    private List<BannerBean> banner;


    private HomeCatgoryAdapter mHomeCatgoryAdapter;


    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        showToolbar();
        return view;
    }

    private void showToolbar() {
        mHomeToolbar.showTitleView();
        mHomeToolbar.setTitle("商品首页");

    }

    @Override
    protected void initData() {
        super.initData();
        initBannerData();
        initRecycleData();
    }

    /**
     * 获取主页中的商品数据
     */
    private void initRecycleData() {
        apiService.getHomeCampaign().enqueue(new Callback<List<HomeCampaign>>() {
            @Override
            public void onResponse(Call<List<HomeCampaign>> call, Response<List<HomeCampaign>> response) {
                final List<HomeCampaign> homeCampaignList = response.body();
                Log.e(TAG, "initRecycleData: " + homeCampaignList.size());
                setHomeCampaign(homeCampaignList);
            }

            @Override
            public void onFailure(Call<List<HomeCampaign>> call, Throwable t) {

            }
        });
    }

    /**
     * 处理主页中商品数据
     *
     * @param homeCampaignList
     */
    private void setHomeCampaign(List<HomeCampaign> homeCampaignList) {
        mHomeCatgoryAdapter = new HomeCatgoryAdapter(getActivity(), homeCampaignList);
        mHomeCatgoryAdapter.setOnCampaignClickListener(new HomeCatgoryAdapter.OnCampaignClickListener() {
            @Override
            public void Onclick(View view, Campaign campaign) {
                ToastUtils.show(getContext(), "已点击该商品：" + campaign.getId());
            }
        });

        mRecycleview.setAdapter(mHomeCatgoryAdapter);
        mRecycleview.addItemDecoration(new CardViewtemDecortion());
        mRecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.e(TAG, "adapter");
    }

    /**
     * 获取banner数据的请求
     */
    private void initBannerData() {
        apiService.getBanner().enqueue(new Callback<List<BannerBean>>() {
            @Override
            public void onResponse(Call<List<BannerBean>> call, Response<List<BannerBean>> response) {
                banner = response.body();
                setBanner(banner);
            }

            @Override
            public void onFailure(Call<List<BannerBean>> call, Throwable t) {

            }
        });
    }

    /**
     * 设置banner，将banner数据以轮播展现出来
     *
     * @param data
     */
    public void setBanner(List<BannerBean> data) {
        List<String> ImgUrls = new ArrayList<>();
        List<String> Titles = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            BannerBean banner = data.get(i);
            String imgUrl = banner.getImgUrl();
            String title = banner.getName();
            ImgUrls.add(imgUrl);
            Titles.add(title);
        }

        mBenner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBenner.setBannerTitles(Titles);
        //设置指示器位置（当banner模式中有指示器时）
        mBenner.setIndicatorGravity(BannerConfig.CENTER);
        /**
         * 设置图片加载器,这里要注意使用ButterKnife时候会出现找不到Banner空件，
         * 会出现空指针的原因。所以这里Banner控件最好是findById出来
         */
        mBenner.setImages(ImgUrls).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        }).start();
    }



}
