package com.example.module0;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hsqbusiness.util.AndroidUtil;


public class Module0MainActivity extends AppCompatActivity {
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
            } else if (R.id.navigation_dashboard == id) {
                mTextMessage.setText(R.string.module0_title_dashboard);
            } else if (R.id.navigation_notifications == id) {
                mTextMessage.setText(R.string.module0_title_notifications);
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module0_main_activity_layout);
        mContext = this;
        try {
            //间隔调试使用
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //每个模块若使用business工具类，需要依赖business
        AndroidUtil.showToast(mContext, "加载business模块成功");
//        long xx = Calendar.getInstance().getTimeInMillis();
////        long xx1 = System.currentTimeMillis();
//        long xx1 = new Date().getTime();
//        Log.e("hsq", "xx=" + xx + ",xx1=" + xx1);

//        String gewg = BuildConfig.API_URL;

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName(mContext, "com.my.flowlayout.JGActivity");
                intent.setComponent(componentName);
                startActivity(intent);

            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}
