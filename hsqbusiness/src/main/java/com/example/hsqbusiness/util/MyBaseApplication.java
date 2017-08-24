package com.example.hsqbusiness.util;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by sqhan on 2017/8/24.
 */

public class MyBaseApplication extends Application {
    private long appStartTime;
    private static MyBaseApplication mAppInstance;

    public static MyBaseApplication getAppInstance() {
        return mAppInstance;
    }

    public MyBaseApplication() {
        super();
        this.appStartTime = System.currentTimeMillis();
    }

    @Override
    protected void attachBaseContext(Context base) {
        MyLogUtil.e("application attachBaseContext start Time : " + System.currentTimeMillis());
        super.attachBaseContext(base);
        mAppInstance = this;
        MultiDex.install(this);//初始化MultiDex

    }
}
