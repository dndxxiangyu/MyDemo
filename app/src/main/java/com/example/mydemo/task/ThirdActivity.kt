package com.example.mydemo.task

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mydemo.R
import kotlinx.android.synthetic.main.activity_second.*

/**
 * MainActivity is missing required documentation.
 */
class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        parseIntent();
    }

    private fun parseIntent() {

//        val bean = intent.getParcelableExtra<RouteBeanCopy>("key")
//        bean?.let {
//            tvSecond.setText(bean.name)
//        }

        tvSecond.setText("third activity")
    }
}
