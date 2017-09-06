package com.example.kotlinmodule.bus;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.hsqbusiness.util.bus.BusObject;
import com.example.kotlinmodule.activity.KotlinActivity;

/**
 * Created by sqhan on 2017/9/6.
 */

public class KotlinMoudleBus extends BusObject {
    public KotlinMoudleBus(String host) {
        super(host);
    }

    @Override
    public Object doDataJob(Context context, String bizName, Object... param) {
        if (TextUtils.isEmpty(bizName)) {
            return null;
        }
        if (bizName.equalsIgnoreCase("KotlinModule/goMain")) {
            Intent intent = new Intent(context, KotlinActivity.class);
            context.startActivity(intent);
        }

        return null;
    }

    @Override
    public void doAsyncDataJob(Context context, String bizName, AsyncCallResultListener resultListener, Object... param) {

    }

    @Override
    public Object doURLJob(Context context, String url) {
        return null;
    }

    @Override
    public void doAsyncURLJob(Context context, String url, AsyncCallResultListener resultListener) {

    }
}
