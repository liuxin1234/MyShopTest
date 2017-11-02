package com.example.administrator.myshoptest.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.activity.TestActivity;
import com.example.administrator.myshoptest.activity.WareDetailActivity;
import com.example.administrator.myshoptest.adapter.HWAdapter;
import com.example.administrator.myshoptest.adapter.baseAdapter.BaseAdapter;
import com.example.administrator.myshoptest.adapter.decoration.DividerItemDecoration;
import com.example.administrator.myshoptest.bean.Page;
import com.example.administrator.myshoptest.bean.Wares;
import com.example.administrator.myshoptest.httpUtils.Api;
import com.example.administrator.myshoptest.httpUtils.Contants;
import com.example.administrator.myshoptest.utils.CartProvider;
import com.example.administrator.myshoptest.utils.ToastUtils;
import com.example.administrator.myshoptest.widget.CnToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


/**
 * 热卖页面
 * Created by Ivan on 15/9/22.
 */
public class HotFragment extends BaseFragment {


    @BindView(R.id.hot_recyclerView)
    RecyclerView mHotRecyclerView;
    @BindView(R.id.refresh_view)
    MaterialRefreshLayout mRefreshView;
    @BindView(R.id.hot_toolbar)
    CnToolbar mHotToolbar;


    private HWAdapter mHWAdapter;

    private int mCurPage = 1;
    private int mPageSize = 10;

    private int mTotaPage = 1;

    //设置下拉刷新的状态
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private static final int STATE_MORE = 2;

    //当前的状态
    private int state = STATE_NORMAL;

    private CartProvider mCartProvider;


    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_hot, null);
        ButterKnife.bind(this, view);
        mCartProvider = new CartProvider(mContext);
        showToolbar();
        return view;
    }

    private void showToolbar() {
        mHotToolbar.showTitleView();
        mHotToolbar.setTitle("热卖商品");
    }

    @Override
    protected void initData() {
        super.initData();
        getHotData();
        initRefreshLayout();
    }

    /**
     * 下拉刷新，上拉加载
     */
    private void initRefreshLayout() {
        //设置加载更多模式
        mRefreshView.setLoadMore(true);
        mRefreshView.setMaterialRefreshListener(new MaterialRefreshListener() {
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
                    Toast.makeText(mContext, "无更多数据", Toast.LENGTH_LONG).show();
                    mRefreshView.finishRefreshLoadMore();
                    mRefreshView.setLoadMore(false);
                }
            }
        });

    }


    /**
     * 下拉刷新数据的方法
     */
    private void refreshData() {
        mCurPage = 1;
        state = STATE_REFREH;
        getHotData();
    }

    /**
     * 上拉加载数据的方法
     */
    private void loadMoreData() {
        mCurPage = ++mCurPage;
        state = STATE_MORE;
        getHotData();
    }

    /**
     * 获取热卖商品的数据
     */
    private void getHotData() {
        Api.SERVICE.postHotWares(mCurPage, mPageSize).enqueue(new Callback<Page<Wares>>() {
            @Override
            public void onResponse(Call<Page<Wares>> call, Response<Page<Wares>> response) {

                final Page<Wares> datas = response.body();
                List<Wares> waresList = datas.getList();
                mTotaPage = datas.getTotalPage();
                showData(waresList);
            }

            @Override
            public void onFailure(Call<Page<Wares>> call, Throwable t) {
                mRefreshView.finishRefresh();
            }
        });
    }

    /**
     * 将数据填充到HotWaresAdapter里
     *
     * @param waresList
     */
    private void showData(final List<Wares> waresList) {
        switch (state) {
            case STATE_NORMAL:
                mHWAdapter = new HWAdapter(getActivity(), waresList);
                mHWAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int position) {
                        Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
                        Wares wares = mHWAdapter.getItem(position);
                        Intent intent = new Intent(getActivity(), WareDetailActivity.class);
                        intent.putExtra(Contants.WARE,wares);
                        startActivity(intent);
                    }
                });

                mHWAdapter.setOnButtonClickListener(new HWAdapter.OnButtonClickListener() {
                    @Override
                    public void OnButtonClickListener(View view, Wares wares) {
                        mCartProvider.put(wares);

                        ToastUtils.show(mContext, "已添加到购物车");
                    }
                });
                mHotRecyclerView.setAdapter(mHWAdapter);
                mHotRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mHotRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mHotRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                        DividerItemDecoration.VERTICAL_LIST));

                break;
            case STATE_REFREH:
                mHWAdapter.clearData();
                mHWAdapter.addData(waresList);
                mHotRecyclerView.scrollToPosition(0);
                mRefreshView.finishRefresh();
                break;
            case STATE_MORE:
                mHWAdapter.addData(mHWAdapter.getDatas().size(), waresList);
                mHotRecyclerView.scrollToPosition(mHWAdapter.getDatas().size());
                mRefreshView.finishRefreshLoadMore();

                break;
            default:
                break;
        }
    }


}
