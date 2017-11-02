package com.example.administrator.myshoptest.httpUtils;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/1/6.
 */

public class Api {
   public static ApiService SERVICE;

    /**
     * 请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10000;

    public static ApiService getDefault(){
        if (SERVICE == null){
            //手动创建一个okHttpClient并设置超长时间
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            /**
             * 对所有请求添加请求头
             */
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response originalResponse = chain.proceed(request);
                    Log.e(TAG, "intercept: 添加请求头 ");
                    return originalResponse.newBuilder()
                            .header("key1","value1")
                            .addHeader("key2","value2")
                            .build();
                }
            });
            SERVICE = new Retrofit.Builder()
                        .client(httpClientBuilder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Contants.API.BASE_URL)
                        .build().create(ApiService.class);
            Log.e(TAG, "getDefault:初始化成功 " );
        }

        return SERVICE;
    }



}
