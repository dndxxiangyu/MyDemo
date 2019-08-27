package com.example.mydemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.MainFragmentAdapter
import com.example.appcenter.AppCenterFragment
import com.example.calendar.CalendarFragment
import com.example.contact.ContactFragment
import com.example.doc.DocFragment
import com.example.feed.FeedFragment
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {
    private lateinit var mRootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        mRootView = view
        init()
        return mRootView
    }

    private fun init() {
        val fragmentList = listOf(FeedFragment(), CalendarFragment(), AppCenterFragment(), DocFragment(), ContactFragment())
        val adapter = MainFragmentAdapter(fragmentList, childFragmentManager)
        mRootView.viewPager.adapter = adapter
    }
}