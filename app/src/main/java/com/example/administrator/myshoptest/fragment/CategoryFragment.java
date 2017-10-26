package com.example.administrator.myshoptest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.adapter.CategoryAdapter;
import com.example.administrator.myshoptest.adapter.CategoryWaresAdapter;
import com.example.administrator.myshoptest.adapter.baseAdapter.BaseAdapter;
import com.example.administrator.myshoptest.adapter.decoration.DividerItemDecoration;
import com.example.administrator.myshoptest.bean.BannerBean;
import com.example.administrator.myshoptest.bean.Category;
import com.example.administrator.myshoptest.bean.Page;
import com.example.administrator.myshoptest.bean.Wares;
import com.example.administrator.myshoptest.httpUtils.Api;
import com.example.administrator.myshoptest.widget.CnToolbar;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 分类界面
 * Created by Ivan on 15/9/22.
 */
public class CategoryFragment extends BaseFragment {
    public static final String TAG = "CategoryFragment";

    @BindView(R.id.recyclerview_category)
    RecyclerView mRecyclerviewCategory;
    @BindView(R.id.slider)
    Banner mSlider;
    @BindView(R.id.recyclerview_wares)
    RecyclerView mRecyclerviewWares;
    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout mRefreshLayout;
    @BindView(R.id.category_toolbar)
    CnToolbar mCategoryToolbar;

    private CategoryAdapter mCategoryAdapter;//一级菜单适配器
    private CategoryWaresAdapter mCategoryWaresAdapter;//二级菜单适配器


    private int mCurPage = 1;
    private int mPageSize = 10;

    private int mTotaPage = 1;

    //设置下拉刷新的状态
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFERH = 1;
    private static final int STATE_HORE = 2;

    //当前的状态
    private int state = STATE_NORMAL;


    private long category_id = 0;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_category, null);
        ButterKnife.bind(this, view);
        initRefreshLayout();
        showToobar();
        return view;
    }

    private void showToobar() {
        mCategoryToolbar.showTitleView();
        mCategoryToolbar.setTitle("商品分类");
    }

    @Override
    protected void initData() {
        super.initData();
        requestCategoryData();
        requestBannerData();
    }

    /**
     * 下拉刷新，上拉加载
     */
    private void initRefreshLayout() {
        //设置加载更多模式
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                /**
                 * 下拉刷新数据
                 */
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                /**
                 * 上拉加载数据
                 */
                if (mCurPage <= mTotaPage) {
                    loadMoreData();
                } else {
                    Log.e(TAG, "mCurPage:" + mCurPage);
                    Log.e(TAG, "mTotaPage:" + mTotaPage);
                    Log.e(TAG, "onRefreshLoadMore: 1111");
                    //这里Toast会重复出现好多次 需要修改
                    // Toast.makeText(mContext, "已经到底了，没有更多数据", Toast.LENGTH_SHORT).show();
//                    setToastShow("已经到底了，没有更多数据");
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });

    }


    /**
     * 下拉刷新数据的方法
     */
    private void refreshData() {
        mCurPage = 1;
        state = STATE_REFERH;
        requestWaresData(category_id);
    }

    /**
     * 上拉加载数据的方法
     */
    private void loadMoreData() {
        mCurPage = ++mCurPage;
        state = STATE_HORE;
        requestWaresData(category_id);
    }


    /**
     * 获取Banner数据
     */
    private void requestBannerData() {
        Api.getDefault().getBanner().enqueue(new Callback<List<BannerBean>>() {
            @Override
            public void onResponse(Call<List<BannerBean>> call, Response<List<BannerBean>> response) {
                if (response.body() == null) {
                    return;
                }
                final List<BannerBean> bannerBeanList = response.body();
                setBanner(bannerBeanList);
            }

            @Override
            public void onFailure(Call<List<BannerBean>> call, Throwable t) {

            }
        });
    }

    /**
     * 设置Banner,并展示
     *
     * @param data
     */
    private void setBanner(List<BannerBean> data) {
        List<String> ImgUrls = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            BannerBean banner = data.get(i);
            String imgUrl = banner.getImgUrl();
            ImgUrls.add(imgUrl);
        }
        /**
         * 设置图片加载器,这里要注意使用ButterKnife时候会出现找不到Banner空件，
         * 会出现空指针的原因。所以这里Banner控件最好是findById出来
         */
        mSlider.setImages(ImgUrls).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        }).start();
    }

    /**
     * 获取商品一级分类数据
     */
    private void requestCategoryData() {
        Api.getDefault().getCategoryList().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.body() == null) {
                    return;
                }
                final List<Category> categories = response.body();
                category_id = categories.get(0).getId();
                requestWaresData(category_id);
                showCategoryData(categories);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    /**
     * 获取二级商品数据
     */
    private void requestWaresData(long categoryId) {
        Api.getDefault().postCategoryWares(categoryId, mCurPage, mPageSize).enqueue(new Callback<Page<Wares>>() {
            @Override
            public void onResponse(Call<Page<Wares>> call, Response<Page<Wares>> response) {
                if (response.body() == null) {
                    return;
                }
                final Page<Wares> waresPage = response.body();
                List<Wares> datas = waresPage.getList();
                showCategoryWares(datas);
            }

            @Override
            public void onFailure(Call<Page<Wares>> call, Throwable t) {

            }
        });
    }

    /**
     * 展示商品一级分类数据
     *
     * @param categories
     */
    private void showCategoryData(List<Category> categories) {
        mCategoryAdapter = new CategoryAdapter(getActivity(), categories);
        mCategoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Category category = mCategoryAdapter.getItem(position);
                category_id = category.getId();
                mCurPage = 1;
                state = STATE_NORMAL;
                requestWaresData(category_id);
            }
        });
        mRecyclerviewCategory.setAdapter(mCategoryAdapter);
        mRecyclerviewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerviewCategory.setItemAnimator(new DefaultItemAnimator());
        mRecyclerviewCategory.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
    }


    /**
     * 展示二级商品
     */
    private void showCategoryWares(List<Wares> waresList) {
        switch (state) {
            case STATE_NORMAL:
                mCategoryWaresAdapter = new CategoryWaresAdapter(getActivity(), waresList);
                mCategoryWaresAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int position) {
                        Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
                    }
                });
                mRecyclerviewWares.setAdapter(mCategoryWaresAdapter);
                mRecyclerviewWares.setLayoutManager(new GridLayoutManager(getContext(), 2));
                mRecyclerviewWares.setItemAnimator(new DefaultItemAnimator());
                mRecyclerviewWares.addItemDecoration(new DividerItemDecoration(getContext(),
                        DividerItemDecoration.VERTICAL_LIST));

                break;
            case STATE_REFERH:
                mCategoryWaresAdapter.clearData();
                mCategoryWaresAdapter.addData(waresList);
                mRecyclerviewWares.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_HORE:
                mCategoryWaresAdapter.addData(mCategoryWaresAdapter.getDatas().size(), waresList);
                mRecyclerviewWares.scrollToPosition(mCategoryWaresAdapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();

                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}



