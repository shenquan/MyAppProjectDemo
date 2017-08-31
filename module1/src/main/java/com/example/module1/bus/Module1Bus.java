package com.example.module1.bus;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.hsqbusiness.util.bus.BusObject;
import com.example.module1.activity.Module1Activity1;


/**
 * Created by sqhan on 2017/8/31.
 */

public class Module1Bus extends BusObject {

    public Module1Bus(String host) {
        super(host);
    }

    @Override
    public Object doURLJob(Context context, String url) {
        return null;
    }

    @Override
    public Object doDataJob(Context context, String bizName, Object... param) {
        if (TextUtils.isEmpty(bizName)) {
            return null;
        }
        if (bizName.equalsIgnoreCase("module2/goMain")) {
            Intent intent = new Intent(context, Module1Activity1.class);
            context.startActivity(intent);
        } else if (bizName.equalsIgnoreCase("module2/logined_do_something")) {

        }
        return null;
    }

    @Override
    public void doAsyncDataJob(Context context, String bizName, AsyncCallResultListener resultListener, Object... param) {

    }

    @Override
    public void doAsyncURLJob(Context context, String url, AsyncCallResultListener resultListener) {

    }
}
