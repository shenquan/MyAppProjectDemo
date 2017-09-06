package com.example.kotlinmodule.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.hsqbusiness.util.android.base.BaseActivity;
import com.example.kotlinmodule.R;

/**
 * Created by sqhan on 2017/9/6.
 */

public class KotlinActivity extends BaseActivity {
    public static String CONST = "常量";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kotlin_main_layout);
        int a = 1;
        long b = 2;
        String c = "3";
    }
}
