package com.example.mydemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.RouteBean
import com.example.mydemo.aidl.server.AidlActivity
import com.example.mydemo.task.SecondActivity
import com.example.mydemo.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity is missing required documentation.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("A", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        initView()
        tvSecondActivity.setOnClickListener {
            val intent = Intent().apply {
                this.setClass(this@MainActivity, SecondActivity::class.java)
                val bean = RouteBean().apply {
                    this.name = "haha"
                    this.keyword = "keyword"
                    this.pageName = "pageName"
                }
                this.putExtra("key", bean)
            }
            startActivity(intent)

        }
        tvAidlActivity.setOnClickListener {
            val intent = Intent().apply {
                this.setClass(this@MainActivity, AidlActivity::class.java)
            }
            startActivity(intent)

        }
        tvWebView.setOnClickListener {
            val intent = Intent().apply {
                this.setClass(this@MainActivity, WebViewActivity::class.java)
            }
            startActivity(intent)
        }
    }

    private fun initView() {
        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.add(android.R.id.content, MainFragment(), "haha")
        ft.commit()
    }

    override fun onResume() {
        super.onResume()
        Log.e("A", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("A", "onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.e("A", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("A", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("A", "onDestroy")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e("A", "onNewIntent")
    }
}
