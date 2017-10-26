package com.example.administrator.myshoptest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 *
 * @author Administrator
 * @date 2017/2/14
 */

public class ShopApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
