package com.example.mydemo.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;

/**
 * 创建时间：2020-02-04
 * author: wuxiangyu.lc
 * describe:
 */
public class AndroidToJS {
    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void callAndroid(String msg) {
        Log.e("AndroidToJS", "JS调用了Android的callAndroid()，msg : " + msg);
    }

    @JavascriptInterface
    public ArrayList<String> callResult(String msg) {
        Log.e("AndroidToJS", "JS调用了Android的callAndroid()，msg : " + msg);
        ArrayList<String> test = new ArrayList<>();
        test.add("haha1");
        test.add("haha2");
        return test;
    }

    @JavascriptInterface
    public void log(String msg) {
        Log.e("AndroidToJS", "JS，msg : " + msg);
    }
}