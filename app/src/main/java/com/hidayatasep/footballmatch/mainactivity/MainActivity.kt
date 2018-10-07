package com.hidayatasep.footballmatch.mainactivity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import app.helper.LocalPreferences
import app.helper.replaceFragmentInActivity
import com.google.gson.Gson
import com.hidayatasep.footballmatch.R
import com.hidayatasep.footballmatch.R.id.*
import com.hidayatasep.footballmatch.listfavorite.ListFavoriteFragment
import com.hidayatasep.footballmatch.listfavorite.ListFavoritePresenter
import com.hidayatasep.footballmatch.listmatch.ListMatchMainFragment
import com.hidayatasep.footballmatch.teams.TeamsFragment
import com.hidayatasep.footballmatch.teams.TeamsPresenter
import com.hidayatasep.latihan2.ApiRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var listFavoriteFragment: ListFavoriteFragment
    private lateinit var listFavoritePresenter: ListFavoritePresenter
    private lateinit var teamsFragment: TeamsFragment
    private lateinit var teamsPresenter: TeamsPresenter
    private lateinit var apiRepository: ApiRepository
    private lateinit var gson: Gson
    private lateinit var localPreferences: LocalPreferences

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            navigation_match -> {
                replaceFragmentInActivity(ListMatchMainFragment.newInstance(), R.id.frame_layout)
                return@OnNavigationItemSelectedListener true
            }
            navigation_teams -> {
                replaceFragmentInActivity(teamsFragment, R.id.frame_layout)
                return@OnNavigationItemSelectedListener true
            }
            navigation_favorites -> {
                replaceFragmentInActivity(listFavoriteFragment, R.id.frame_layout)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.findFragmentById(R.id.frame_layout)
                as ListMatchMainFragment? ?: ListMatchMainFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.frame_layout)
        }

        listFavoriteFragment = ListFavoriteFragment.newInstance()
        listFavoritePresenter = ListFavoritePresenter(this, listFavoriteFragment)

        apiRepository = ApiRepository()
        gson = Gson()
        localPreferences = LocalPreferences.getInstance(this)
        teamsFragment = TeamsFragment.newInstance()
        teamsPresenter = TeamsPresenter(teamsFragment, apiRepository, gson, localPreferences)


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

}
