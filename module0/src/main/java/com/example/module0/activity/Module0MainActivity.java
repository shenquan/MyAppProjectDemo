package com.example.module0.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hsqbusiness.util.AndroidUtil;
import com.example.hsqbusiness.util.MyBaseApplication;
import com.example.hsqbusiness.util.android.base.BaseActivity;
import com.example.hsqbusiness.util.eventbusmessage.MyMessage;
import com.example.module0.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class Module0MainActivity extends BaseActivity {
    private Context mContext;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //在library project中不能使用switch
            //http://tools.android.com/tips/non-constant-fields
            //https://stackoverflow.com/questions/15247606/intellij-android-java-constant-expression-required-on-case-r-id-viewid
            /*switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.module0_title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.module0_title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.module0_title_notifications);
                    return true;
            }*/

            int id = item.getItemId();
            if (R.id.navigation_home == id) {
                mTextMessage.setText(R.string.module0_title_home);
                return true;//需要返回true
            } else if (R.id.navigation_dashboard == id) {
                mTextMessage.setText(R.string.module0_title_dashboard);
                return true;
            } else if (R.id.navigation_notifications == id) {
                mTextMessage.setText(R.string.module0_title_notifications);
                return true;
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.module0_main_activity_layout);
        mContext = this;
        MyBaseApplication.getInstance().isHomeCreated = true;

        /*try {
            //间隔调试使用
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //每个模块若使用business工具类，需要依赖business
        AndroidUtil.showToast(mContext, "加载business模块成功");
//        long xx = Calendar.getInstance().getTimeInMillis();
////        long xx1 = System.currentTimeMillis();
//        long xx1 = new Date().getTime();
//        Log.e("hsq", "xx=" + xx + ",xx1=" + xx1);

//        String gewg = BuildConfig.API_URL;

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextMessage.setText("Module0MainActivity：点击我跳转");
        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName(mContext, "com.my.flowlayout.JGActivity");
                intent.setComponent(componentName);
                startActivity(intent);

                /*Intent intent1 = new Intent(mContext, SplashActivity.class);
                startActivity(intent1);*/

            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent1111(MyMessage messageEvent) {
//        tv_message.setText(messageEvent.getMessage());

        switch (messageEvent.type) {
            case 1:
                String str1 = "Eventbus1:" + messageEvent.text;
                AndroidUtil.showToast(mContext, str1);
                mTextMessage.setText(str1);

                break;
            case 2:
                String str2 = "Eventbus2:" + messageEvent.text;
                AndroidUtil.showToast(mContext, str2);
                mTextMessage.setText(str2);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}