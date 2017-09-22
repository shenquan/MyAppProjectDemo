package com.example.scrollwebviewdemo.bus;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.hsqbusiness.bus.BusObject;
import com.example.scrollwebviewdemo.ScrollWebViewActivity;

/**
 * Created by sqhan on 2017/9/22.
 */

public class ModuleBus extends BusObject {
    public ModuleBus(String host) {
        super(host);
    }

    @Override
    public Object doDataJob(Context context, String bizName, Object... param) {
        if (TextUtils.isEmpty(bizName)) {
            return null;
        }
        if (bizName.equalsIgnoreCase("scrollDemo/goMain")) {
            Intent intent = new Intent(context, ScrollWebViewActivity.class);
            context.startActivity(intent);
        } else if (bizName.equalsIgnoreCase("module1/logined_do_something")) {

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
