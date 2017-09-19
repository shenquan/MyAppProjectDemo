package com.example.kotlinmodule.bus

import android.content.Context
import android.content.Intent
import android.text.TextUtils

import com.example.hsqbusiness.bus.BusObject
import com.example.kotlinmodule.activity.KotlinActivity

/**
 * Created by sqhan on 2017/9/6.
 */

class KotlinMoudleBus(host: String) : BusObject(host) {

    override fun doDataJob(context: Context, bizName: String, vararg param: Any): Any? {
        if (TextUtils.isEmpty(bizName)) {
            return null
        }
        if (bizName.equals("KotlinModule/goMain", ignoreCase = true)) {
            val intent = Intent(context, KotlinActivity::class.java)
            context.startActivity(intent)
        }

        return null
    }

    override fun doAsyncDataJob(context: Context, bizName: String, resultListener: BusObject.AsyncCallResultListener, vararg param: Any) {

    }

    override fun doURLJob(context: Context, url: String): Any? {
        return null
    }

    override fun doAsyncURLJob(context: Context, url: String, resultListener: BusObject.AsyncCallResultListener) {

    }
}
