package com.example.hsqbusiness.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liangjj on 2015/12/21.
 */
public class AndroidUtil {
    public static final String TAG = "hsq";
    private Context context;
    public static final int FILE_SELECT_CODE = 10001;
    public static final String NAME = "Ddt_share_pref";
    public static final String UNREAD = "vbk.im.unread.message";

    public AndroidUtil(Context context) {
        this.context = context;
    }

    public static Toast mToast;

    //只显示一个toast，by韩申权
    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }


}
