package com.example

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainFragmentAdapter(private val listFragment: List<Fragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment {
        return listFragment[p0]
    }

    override fun getCount(): Int {
        return listFragment.size
    }
}