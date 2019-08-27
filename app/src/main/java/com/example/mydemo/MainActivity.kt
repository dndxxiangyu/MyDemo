package com.example.mydemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

/**
 * MainActivity is missing required documentation.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.add(android.R.id.content, MainFragment(), "haha")
        ft.commit()
    }
}
