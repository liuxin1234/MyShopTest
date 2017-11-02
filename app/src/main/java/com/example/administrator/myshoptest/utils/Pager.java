//package com.example.administrator.myshoptest.utils;
//
//import android.content.Intent;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.cjj.MaterialRefreshLayout;
//import com.cjj.MaterialRefreshListener;
//import com.example.administrator.myshoptest.activity.TestActivity;
//import com.example.administrator.myshoptest.adapter.HWAdapter;
//import com.example.administrator.myshoptest.adapter.baseAdapter.BaseAdapter;
//import com.example.administrator.myshoptest.adapter.decoration.DividerItemDecoration;
//import com.example.administrator.myshoptest.bean.Page;
//import com.example.administrator.myshoptest.bean.Wares;
//import com.example.administrator.myshoptest.httpUtils.Api;
//
//import java.util.HashMap;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
//
///**
// * Created by
// * 项目名称：com.example.administrator.myshoptest.fragment
// * 项目日期：2017/10/26
// * 作者：liux
// * 功能：
// */
//
//public class Pager {
//
//    private static Builder builder;
//
//
//
//    //设置下拉刷新的状态
//    private static final int STATE_NORMAL = 0;
//    private static final int STATE_REFREH = 1;
//    private static final int STATE_MORE = 2;
//    //当前的状态
//    private int state = STATE_NORMAL;
//
//    private Pager(){
//
//    }
//
//    public static Builder newBuilder(){
//
//        return new Builder();
//    }
//
//    /**
//     * 下拉刷新，上拉加载
//     */
//    private void initRefreshLayout() {
//        //设置加载更多模式
//        builder.mRefreshLayout.setLoadMore(true);
//        builder.mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
//            @Override
//            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                /**
//                 * 下拉刷新数据
//                 */
//                refreshData();
//            }
//
//            @Override
//            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
//                /**
//                 * 上拉加载数据
//                 */
//                if (mCurPage <= mTotaPage) {
//                    loadMoreData();
//                } else {
//                    //Toast.makeText(mContext, "已经到底了，没有更多数据", Toast.LENGTH_SHORT).show();
////                    setToastShow("已经到底了，没有更多数据");
//                    mRefreshView.finishRefreshLoadMore();
//                }
//            }
//        });
//
//    }
//
//
//    /**
//     * 下拉刷新数据的方法
//     */
//    private void refreshData() {
//        builder.pageIndex = 1;
//        state = STATE_REFREH;
//        requestHotData();
//    }
//
//    /**
//     * 上拉加载数据的方法
//     */
//    private void loadMoreData() {
//        builder.pageIndex = 1;
//        state = STATE_MORE;
//        requestHotData();
//    }
//
//
//    /**
//     * 获取热卖商品的数据
//     */
//    private void requestHotData() {
//        builder
//        Api.SERVICE.postHotWares(mCurPage, mPageSize).enqueue(new Callback<Page<Wares>>() {
//            @Override
//            public void onResponse(Call<Page<Wares>> call, Response<Page<Wares>> response) {
//
//                final Page<Wares> datas = response.body();
//                List<Wares> waresList = datas.getList();
//                mTotaPage = datas.getTotalPage();
//                showData(waresList);
//            }
//
//            @Override
//            public void onFailure(Call<Page<Wares>> call, Throwable t) {
//                mRefreshView.finishRefresh();
//            }
//        });
//    }
//
//
//    /**
//     * 将数据填充到HotWaresAdapter里
//     *
//     */
//    private <T> void showData(List<T> datas,int totalPage,int totalCount) {
//        switch (state) {
//            case STATE_NORMAL:
//                if (builder.mOnPageListener != null){
//                    builder.mOnPageListener.load(datas,totalPage,totalCount);
//                }
//
//                break;
//            case STATE_REFERH:
//                if (builder.mOnPageListener != null){
//                    builder.mOnPageListener.refresh(datas,totalPage,totalCount);
//                }
//                builder.mRefreshLayout.finishRefresh();
//                break;
//            case STATE_HORE:
//                if (builder.mOnPageListener != null){
//                    builder.mOnPageListener.loadMore(datas,totalPage,totalCount);
//                }
//                builder.mRefreshLayout.finishRefreshLoadMore();
//                break;
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 这里主要是封装了一些会动的东西
//     */
//    public static class Builder{
//
//        private String url;
//
//        private int totalPage = 1;
//        private int pageIndex = 1;
//        private int pageSize = 10;
//
//        private MaterialRefreshLayout mRefreshLayout;
//        private boolean canLoadMore;
//        private OnPageListener mOnPageListener;
//
//        private HashMap<String,Object> params = new HashMap<>(5);
//
//        public Builder setUrl(String url){
//            this.url = url;
//            return builder;
//        }
//
//        public Builder putParam(String key,Object value){
//            params.put(key,value);
//            return builder;
//        }
//
//        public Builder setPageSize(int pageSize){
//            this.pageSize = pageSize;
//            return builder;
//        }
//
//        public Builder setRefreshLayout(MaterialRefreshLayout refreshLayout){
//            this.mRefreshLayout = refreshLayout;
//            return builder;
//        }
//
//        public Builder setLoadMore(boolean loadMore){
//            this.canLoadMore = loadMore;
//            return builder;
//        }
//
//        public Builder setOnPageListener(OnPageListener onPageListener){
//            this.mOnPageListener = onPageListener;
//            return builder;
//        }
//        public Pager builder(){
//            return null;
//        }
//
//    }
//
//
//    public interface OnPageListener<T>{
//        void load(List<T> datas,int totalPage,int totalCount);
//        void refresh(List<T> datas,int totalPage,int totalCount);
//        void loadMore(List<T> datas,int totalPage,int totalCount);
//
//    }
//
//}
