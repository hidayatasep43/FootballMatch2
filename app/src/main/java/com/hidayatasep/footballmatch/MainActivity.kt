package com.hidayatasep.footballmatch

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import app.helper.replaceFragmentInActivity
import app.webservice.ApiRepository
import com.google.gson.Gson
import com.hidayatasep.footballmatch.R.id.*
import com.hidayatasep.footballmatch.listfavorite.ListFavoriteMainFragment
import com.hidayatasep.footballmatch.listmatch.ListMatchMainFragment
import com.hidayatasep.footballmatch.search.SearchActivity
import com.hidayatasep.footballmatch.teams.TeamsFragment
import com.hidayatasep.footballmatch.teams.TeamsPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var menuItem: Menu? = null

    private lateinit var teamsFragment: TeamsFragment
    private lateinit var teamsPresenter: TeamsPresenter
    private lateinit var apiRepository: ApiRepository
    private lateinit var gson: Gson
    private var typeSearch: Int = SearchActivity.TYPE_SEARCH_MATCH

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            navigation_match -> {
                replaceFragmentInActivity(ListMatchMainFragment.newInstance(), R.id.frame_layout)
                showSearchMenu(true)
                typeSearch = SearchActivity.TYPE_SEARCH_MATCH
                return@OnNavigationItemSelectedListener true
            }
            navigation_teams -> {
                replaceFragmentInActivity(teamsFragment, R.id.frame_layout)
                showSearchMenu(true)
                typeSearch = SearchActivity.TYPE_SEARCH_TEAM
                return@OnNavigationItemSelectedListener true
            }
            navigation_favorites -> {
                replaceFragmentInActivity(ListFavoriteMainFragment.newInstance(), R.id.frame_layout)
                showSearchMenu(false)
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

        apiRepository = ApiRepository()
        gson = Gson()
        teamsFragment = TeamsFragment.newInstance()
        teamsPresenter = TeamsPresenter(teamsFragment, apiRepository, gson)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            menu_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("search", typeSearch)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSearchMenu(isShowing: Boolean) {
        if (isShowing) {
            menuItem?.getItem(0)?.setVisible(true)
        } else {
            menuItem?.getItem(0)?.setVisible(false)
        }
    }

}
