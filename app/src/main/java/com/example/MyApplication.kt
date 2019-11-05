package com.example

import android.app.Application
import android.util.Log

/**
 * 创建时间：2019-11-04
 * author: wuxiangyu.lc
 * describe:
 */
class MyApplication: Application() {
    companion object {
        const val TAG = "wxy"
    }
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
        registerActivityLifecycleCallbacks(MyLifeCycle())

    }
}