package com.example

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

/**
 * 创建时间：2019-11-04
 * author: wuxiangyu.lc
 * describe:
 */
class MyLifeCycle : Application.ActivityLifecycleCallbacks {
    companion object {
        const val TAG = "wxy"
    }

    override fun onActivityPaused(activity: Activity?) {
        Log.i(TAG, "onActivityPaused: " + activity?.javaClass?.name)
    }

    override fun onActivityResumed(activity: Activity?) {
        Log.i(TAG, "onActivityResumed: ")
    }

    override fun onActivityStarted(activity: Activity?) {
        Log.i(TAG, "onActivityStarted")
    }

    override fun onActivityDestroyed(activity: Activity?) {
        Log.i(TAG, "onActivityDestroyed")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        Log.i(TAG, "onActivitySaveInstanceState")
    }

    override fun onActivityStopped(activity: Activity?) {
        Log.i(TAG, "onActivityStopped")
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Log.i(TAG, "onActivityCreated" + activity?.packageName + ": " + activity?.componentName?.className)
    }
}