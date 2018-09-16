package com.hidayatasep.footballmatch.mainactivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by hidayatasep43 on 9/16/2018.
 * hidayatasep43@gmail.com
 */
class MainPagerAdapter (fragmentManager: FragmentManager,
                        private val listMatchFragmentPrev: ListMatchFragment,
                        private val listMatchFragmentNext: ListMatchFragment
                        )
    : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? {
        if (position == 0) {
            return listMatchFragmentPrev
        } else {
            return listMatchFragmentNext
        }
    }

    override fun getCount(): Int = 2
}