package com.example.mydemo.util

import android.content.Context

object AppUtil {
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }
}