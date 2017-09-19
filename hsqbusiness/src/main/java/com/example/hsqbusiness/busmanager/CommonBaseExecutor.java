package com.example.hsqbusiness.busmanager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.hsqbusiness.MyBaseApplication;
import com.example.hsqbusiness.android.activity.TestActivity;

/**
 * 非bus核心，只是基础工具类而已
 */
public class CommonBaseExecutor {
    private static CommonBaseExecutor instance;

    private CommonBaseExecutor() {

    }

    public static CommonBaseExecutor getInstance() {
        if (instance == null) {
            instance = new CommonBaseExecutor();
        }

        return instance;
    }

    //example
    public void goHome(Context context, int homeIndex) {
        if (!MyBaseApplication.getInstance().isHomeCreated) {
            Intent intent = new Intent(context, TestActivity.class);
            intent.putExtra("START_HOME", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        } else {
//            Intent intent = new Intent(MyBaseApplication.getInstance(), (Class) Bus.callData(null, "home/GET_HOME_CLASSNAME"));

            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(MyBaseApplication.getInstance(), "com.example.module0.Module0MainActivity");
            intent.setComponent(componentName);

            intent.putExtra("HOME_PAGE_INDEX", homeIndex);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    public void quit(Context context) {
        /*Intent intent = new Intent(context, SplashActivity.class);
        intent.putExtra("EXIT_APP", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);*/
    }


}
