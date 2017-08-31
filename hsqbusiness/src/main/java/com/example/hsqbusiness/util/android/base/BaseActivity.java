package com.example.hsqbusiness.util.android.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.hsqbusiness.util.bus.Bus;

/**
 * Created by sqhan on 2017/8/30.
 */

public class BaseActivity extends FragmentActivity {
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    /**
     * 返回首页
     *
     * @param homeIndex
     */
    public void goHome(int homeIndex) {
        Bus.callData(this, "common/base_goHome", new Integer(homeIndex));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
