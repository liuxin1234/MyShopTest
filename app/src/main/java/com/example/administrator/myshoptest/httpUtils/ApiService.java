package com.example.administrator.myshoptest.httpUtils;

import com.example.administrator.myshoptest.bean.BannerBean;
import com.example.administrator.myshoptest.bean.Category;
import com.example.administrator.myshoptest.bean.HomeCampaign;
import com.example.administrator.myshoptest.bean.Page;
import com.example.administrator.myshoptest.bean.Wares;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.example.administrator.myshoptest.httpUtils.Contants.API.CAMPAIGN_HOME;
import static com.example.administrator.myshoptest.httpUtils.Contants.API.CATEGORY_LIST;
import static com.example.administrator.myshoptest.httpUtils.Contants.API.WARES_HOT;
import static com.example.administrator.myshoptest.httpUtils.Contants.API.WARES_LIST;

/**
 * Created by Administrator on 2017/1/10.
 */
public interface ApiService {

    /**
     * 单独的给请求增加请求头
     * 用的是rxjava
     */
    @Headers("apikey:b86c2269f")
    @GET("/student/login")
    Call<HttpResult> login(@Query("phone") String phone,
                           @Query("password") String password);

    /**
     * 不单独的给请求增加请求头
     * 使用同一的请求头
     */
    @GET("/student/login")
    Call<HttpResult> login2(@Query("phone") String phone,
                                  @Query("password") String password);

    /**
     * 首页广告轮播
     * @return
     */
    @GET("banner/query?type=1")
    Call<List<BannerBean>> getBanner();

    /**
     * 首页热门活动
     * @return
     */
    @GET(CAMPAIGN_HOME)
    Call<List<HomeCampaign>> getHomeCampaign();

    /**
     * 热卖商品
     * @param curPage
     * @param pageSize
     * @return
     */
    @GET(WARES_HOT)
    Call<Page<Wares>> postHotWares(@Query("curPage") int curPage,
                                   @Query("pageSize") int pageSize);

    /**
     * 商品一级分类
     * @return
     */
    @GET(CATEGORY_LIST)
    Call<List<Category>> getCategoryList();

    /**
     * 获取分类下的商品
     */
    @GET(WARES_LIST)
    Call<Page<Wares>> postCategoryWares(@Query("categoryId") long categoryId,
                                        @Query("curPage") int curPage,
                                        @Query("pageSize") int pageSize);

    /**
     * 获取热门活动下的商品
     */




}
