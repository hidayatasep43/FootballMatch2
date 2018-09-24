package com.hidayatasep.footballmatch.mainactivity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import app.helper.LocalPreferences
import com.google.gson.Gson
import com.hidayatasep.footballmatch.R
import com.hidayatasep.footballmatch.R.id.*
import com.hidayatasep.footballmatch.listfavorite.ListFavoriteFragment
import com.hidayatasep.footballmatch.listfavorite.ListFavoritePresenter
import com.hidayatasep.footballmatch.listmatch.ListMatchFragment
import com.hidayatasep.footballmatch.listmatch.ListMatchPresenter
import com.hidayatasep.latihan2.ApiRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: MainPagerAdapter
    private lateinit var listMatchFragmentPrev: ListMatchFragment
    private lateinit var listMatchFragmentNext: ListMatchFragment
    private lateinit var listFavoriteFragment: ListFavoriteFragment
    private lateinit var listMatchPresenterPrev: ListMatchPresenter
    private lateinit var listMatchPresenterNext: ListMatchPresenter
    private lateinit var listFavoritePresenter: ListFavoritePresenter

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            navigation_prev -> {
                viewPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            navigation_next -> {
                viewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            navigation_favorites -> {
                viewPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val request = ApiRepository()
        val gson = Gson()
        val localPreferences = LocalPreferences.getInstance(this)

        listMatchFragmentPrev = ListMatchFragment.newInstance()
        listMatchFragmentNext = ListMatchFragment.newInstance()
        listFavoriteFragment = ListFavoriteFragment.newInstance()
        listMatchPresenterPrev = ListMatchPresenter(listMatchFragmentPrev, request, gson, localPreferences, TYPE_LIST_PREV)
        listMatchPresenterNext = ListMatchPresenter(listMatchFragmentNext, request, gson, localPreferences, TYPE_LIST_NEXT)
        listFavoritePresenter = ListFavoritePresenter(this, listFavoriteFragment)
        pagerAdapter = MainPagerAdapter(supportFragmentManager, listMatchFragmentPrev, listMatchFragmentNext, listFavoriteFragment)
        viewPager.adapter = pagerAdapter

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    companion object {
        const val TYPE_LIST_PREV = 1
        const val TYPE_LIST_NEXT = 2
        const val TYPE_LIST_FAVORITE = 3
    }

}
