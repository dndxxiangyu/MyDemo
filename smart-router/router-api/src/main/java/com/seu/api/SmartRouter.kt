package com.seu.api

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

/**
 * 创建时间：2019-12-26
 * author: wuxiangyu.lc
 * describe:
 */
object SmartRouter {
    const val TAG = "SmartRouter";
    private var mContext: Context? = null
    val activityRouterMap = TreeMap<String, String>()
    fun init(context: Context) {
        // merge all map from every module
        mContext = context
    }

    fun navigation(routerUrl: String) {
        try {
            val activityClazz = activityRouterMap[routerUrl] ?: return
            val intent = Intent()
            intent.setClassName(mContext?.packageName?: "", activityClazz)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext?.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "routerUrl error")
        }
    }
}