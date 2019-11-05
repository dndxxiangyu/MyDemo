package com.example.feed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_okhttp.*
import java.net.InetAddress

/**
 * 创建时间：2019-10-31
 * author: wuxiangyu.lc
 * describe:
 */
class OkhttpTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp)
        btnGetResponse.setOnClickListener {
            httpRequest()
        }
    }

    private fun httpRequest() {
        Thread() {
            val startTime = System.currentTimeMillis()
            val list = InetAddress.getAllByName("http:www.baidu.com")
            list?.let { array ->
                for (it in array) {
                    Log.i("wxy", "")
                }
            }
            Log.i("Wxy", "time: ${System.currentTimeMillis() - startTime}")
        }.start()

    }
}