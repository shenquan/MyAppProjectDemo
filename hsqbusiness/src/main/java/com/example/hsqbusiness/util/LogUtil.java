package com.example.hsqbusiness.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by sqhan on 2017/6/16.
 */

public class LogUtil {
    private static final String TAG = "hsq";
    private static boolean showLog = true;//此处默认显示为true，用Freeline好调试

    public LogUtil() {
    }

    public static void e(String msg) {
        if (showLog) {
            if (!TextUtils.isEmpty(msg)) {
                Log.e(TAG, msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (showLog) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
                Log.e(tag, msg);
            }
        }
    }

    //强制显示日志:force
    public static void f(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Log.e(TAG, msg);
        }
    }

    public static void f(String tag, String msg) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }


    public static boolean isShowLog() {
        return showLog;
    }

    public static void setShowLog(boolean showLog) {
        LogUtil.showLog = showLog;
    }
}
