package com.example.hsqbusiness.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.hsqbusiness.R;
import com.example.hsqbusiness.android.base.BaseActivity;

/**
 * Created by sqhan on 2017/8/31.
 */

public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
    }
}
