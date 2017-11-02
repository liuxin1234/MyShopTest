package com.example.administrator.myshoptest;


import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mob.MobApplication;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 *
 * @author Administrator
 * @date 2017/2/14
 */

public class ShopApplication extends MobApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Logger.addLogAdapter(new AndroidLogAdapter());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
