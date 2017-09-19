package com.example.hsqbusiness;

import android.text.TextUtils;
import android.util.Log;

import com.example.hsqbusiness.util.AndroidUtil;

/**
 * Created by sqhan on 2017/6/16.
 */

public class MyLogUtil {
    private static final String TAG = AndroidUtil.TAG;
    private static boolean showLog = true;//此处默认显示为true，用Freeline好调试

    public MyLogUtil() {
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


    public static boolean isShowLog() {
        return showLog;
    }

    public static void setShowLog(boolean showLog) {
        MyLogUtil.showLog = showLog;
    }
}
