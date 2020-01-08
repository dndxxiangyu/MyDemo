package com.example.mydemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.RouteBean
import com.example.mydemo.aidl.server.AidlActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity is missing required documentation.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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
    }

    private fun initView() {
        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.add(android.R.id.content, MainFragment(), "haha")
        ft.commit()
    }
}
