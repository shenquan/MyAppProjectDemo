package com.example.kotlinmodule.activity

import android.os.Bundle
import com.example.hsqbusiness.util.android.base.BaseActivity
import com.example.hsqbusiness.util.util.AndroidUtil
import com.example.hsqbusiness.util.util.LogUtil
import com.example.kotlinmodule.R
import kotlinx.android.synthetic.main.kotlin_main_layout.*

/**
 * Created by sqhan on 2017/9/6.
 */

class KotlinActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_main_layout)
        val a = 1
        val b: Long = 2
        val c = "3"
        val d = 3L

        LogUtil.e("" + a + b + c + CONST + d)

        tv.setText("kotlin扩展插件设置该textview的值")
//        tv.visibility=8//隐藏控件
        tv.setOnClickListener {
            AndroidUtil.showToast(mContext, "点击了该字段")
        }


    }

    companion object {
        var CONST = "常量"
    }
}
