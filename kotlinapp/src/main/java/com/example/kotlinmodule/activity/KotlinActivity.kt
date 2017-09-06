package com.example.kotlinmodule.activity

import android.os.Bundle
import com.example.hsqbusiness.util.android.base.BaseActivity
import com.example.hsqbusiness.util.util.LogUtil
import com.example.kotlinmodule.R

/**
 * Created by sqhan on 2017/9/6.
 */

class KotlinActivity : BaseActivity() {

    //todo  该模块的manifest中不知怎么引用这个，所以启动该模块的app失败

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_main_layout)
        val a = 1
        val b: Long = 2
        val c = "3"
        val d = 3L

        LogUtil.e("" + a + b + c + CONST + d)
    }

    companion object {
        var CONST = "常量"
    }
}
