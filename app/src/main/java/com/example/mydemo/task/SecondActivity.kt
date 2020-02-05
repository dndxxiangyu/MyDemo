package com.example.mydemo.task

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.mydemo.MainActivity
import com.example.mydemo.R
import kotlinx.android.synthetic.main.activity_second.*

/**
 * MainActivity is missing required documentation.
 */
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.e("B", "onCreate")
        parseIntent();
    }

    private fun parseIntent() {

//        val bean = intent.getParcelableExtra<RouteBeanCopy>("key")
//        bean?.let {
//            tvSecond.setText(bean.name)
//        }

        tvSecond.setOnClickListener {

            val intent = Intent()
            intent.setClass(this@SecondActivity, MainActivity::class.java)
            startActivity(intent)
        }
        tvSecond.setText("second activity")
    }

    override fun onResume() {
        super.onResume()
        Log.e("B", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("B", "onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.e("B", "onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.e("B", "onStop")
    }
}
