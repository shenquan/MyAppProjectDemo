package com.example.hsqbusiness;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.hsqbusiness.util.AndroidUtil;
import com.example.hsqbusiness.util.LogUtil;
import com.tencent.bugly.Bugly;

/**
 * Created by sqhan on 2017/8/24.
 */

public class MyBaseApplication extends Application {
    private long appStartTime;
    private static MyBaseApplication mAppInstance;
    public boolean isHomeCreated = false;// 首页启动（用于跳转推送进入）

    public static MyBaseApplication getInstance() {
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

    @Override
    public void onCreate() {
        super.onCreate();
        if (AndroidUtil.isMainProcess(this)) {
            LogUtil.f("hsqdeb", "是主进程");
            if (AndroidUtil.isApkDebug()) {
                Bugly.init(this, "f4d1ea1cfe", false);//debug版
                LogUtil.f("hsqdeb", "debug版");
            } else {
                Bugly.init(this, "334612f0b0", false);//release版
                LogUtil.f("hsqdeb", "release版");
            }
        } else {
            LogUtil.f("hsqdeb", "不是主进程");
        }
    }
}
