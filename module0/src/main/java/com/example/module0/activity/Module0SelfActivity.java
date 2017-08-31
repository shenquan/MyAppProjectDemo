package com.example.module0.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hsqbusiness.util.eventbusmessage.MyMessage;
import com.example.module0.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sqhan on 2017/8/22.
 */

public class Module0SelfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module0_self_activity_layout);

        EventBus.getDefault().post(new MyMessage(1, "欢迎1"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(new MyMessage(2, "欢迎2"));
            }
        }).start();


    }


}
