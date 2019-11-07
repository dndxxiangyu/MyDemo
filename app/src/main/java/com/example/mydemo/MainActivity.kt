package com.example.mydemo

import android.app.ActivityManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

/**
 * MainActivity is missing required documentation.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        initView()
        Log.i("wxy1", "onCreate")
    }

    private fun initView() {
        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.add(android.R.id.content, MainFragment(), "haha")
        ft.commit()
    }

    override fun onStop() {
        Log.i("wxy1", "onStop")
        super.onStop()
    }

    override fun onPause() {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val currentClassName = manager.getRunningTasks(1).get(0).topActivity.getPackageName()
        Log.i("wxy1", currentClassName)
        Log.i("wxy1", "onPause")
        super.onPause()
    }

    override fun onBackPressed() {
        Log.i("wxy1", "onBackPressed")
        //点击back然后走到了onpause，这时候不需要打印。
        super.onBackPressed()
    }

    override fun finish() {
        Log.i("wxy1", "finish")
        super.finish()
    }
}
