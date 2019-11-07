package com.example

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log


/**
 * 创建时间：2019-11-04
 * author: wuxiangyu.lc
 * describe:
 */
class MyLifeCycle : Application.ActivityLifecycleCallbacks {
    companion object {
        const val TAG = "wxycc"
    }

    override fun onActivityPaused(activity: Activity?) {
        Log.i(
            TAG,
            "onActivityPaused: " + isApplicationForeground(activity, activity?.packageName ?: "")
        )
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
        Log.i(
            TAG,
            "onActivityStopped, " + isApplicationForeground(activity, activity?.packageName ?: "")
        )
        activity?.applicationContext?.let {
            getCurrentProcessName(it)
        }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Log.i(
            TAG,
            "onActivityCreated" + activity?.packageName + ": " + activity?.componentName?.className
        )
    }

    private fun getCurrentProcessName(context: Context): String {
        val pid = android.os.Process.myPid()
        var processName = ""
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid == pid) {
                processName = process.processName
            }
        }
        return processName
    }

    fun isApplicationForeground(context: Context?, packageName: String): Boolean {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return false
        }
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (Build.VERSION.SDK_INT < 21) {//5.0
            val tasks = am.getRunningTasks(1)
            if (!tasks.isEmpty()) {
                val topActivity = tasks[0].topActivity
                if (topActivity != null && packageName == topActivity.packageName) {
                    return true
                }
            }
        } else {
            val processInfos = am.runningAppProcesses
            for (processInfo in processInfos) {
                if (packageName == processInfo.processName) {
                    // 主进程
                    return processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                }
            }
        }
        return false
    }

}