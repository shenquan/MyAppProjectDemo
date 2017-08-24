package com.example.sqhan.myapplication1;

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

import com.my.flowlayout.JGActivity;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);

                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);
        mContext = this;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        AndroidUtil.showToast(MainActivity.this, "依赖成功");
//        long xx = Calendar.getInstance().getTimeInMillis();
////        long xx1 = System.currentTimeMillis();
//        long xx1 = new Date().getTime();
//        Log.e("hsq", "xx=" + xx + ",xx1=" + xx1);

//        String gewg = BuildConfig.API_URL;

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AndroidUtil.showToast(MainActivity.this, "打开新的Activity");
                *//*Intent intent = new Intent(MainActivity.this, SelfActivity.class);
                startActivity(intent);*//*
                Intent intent = new Intent(MainActivity.this, Activity1.class);
                startActivity(intent);*/

                Intent intent = new Intent(mContext, JGActivity.class);
                startActivity(intent);


            }
        });
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
