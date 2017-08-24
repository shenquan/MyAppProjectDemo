package com.example.module1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.module1.R;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        String gewg = "xx";
        String gew1g = "xx";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("hsq", "Activity1销毁");
    }
}
