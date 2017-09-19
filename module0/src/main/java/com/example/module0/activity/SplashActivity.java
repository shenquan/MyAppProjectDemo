package com.example.module0.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.hsqbusiness.android.base.BaseActivity;
import com.example.hsqbusiness.eventbusmessage.MyMessage;
import com.example.module0.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by sqhan on 2017/8/31.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        TextView tv = (TextView) findViewById(R.id.tv);
        if (isDebug()) {
            tv.setText("Debug版SPLASH页面\n点击进入首页");
        } else {
            tv.setText("Release版SPLASH页面\n点击进入首页");
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Module0MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent1111(MyMessage messageEvent) {

    }

    /**
     * 判断是否为debug环境
     *
     * @return
     */
    private boolean isDebug() {
        ApplicationInfo info = this.getApplicationInfo();
        boolean isDubug = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0 ? true : false;
        return isDubug;
    }
}
