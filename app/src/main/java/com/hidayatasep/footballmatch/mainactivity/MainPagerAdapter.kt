package com.hidayatasep.footballmatch.mainactivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.hidayatasep.footballmatch.listfavorite.ListFavoriteFragment
import com.hidayatasep.footballmatch.listmatch.ListMatchFragment

/**
 * Created by hidayatasep43 on 9/16/2018.
 * hidayatasep43@gmail.com
 */
class MainPagerAdapter (fragmentManager: FragmentManager,
                        val listMatchFragmentPrev: ListMatchFragment,
                        val listMatchFragmentNext: ListMatchFragment,
                        val listFavoriteFragment: ListFavoriteFragment
)
    : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? {

        return if (position == 0) {
            listMatchFragmentPrev
        } else if (position == 1){
            listMatchFragmentNext
        } else {
            listFavoriteFragment
        }
    }

    override fun getCount(): Int = 3
}