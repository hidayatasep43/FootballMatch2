package com.hidayatasep.footballmatch.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import app.data.Event
import app.data.Team
import app.webservice.ApiRepository
import com.google.gson.Gson
import com.hidayatasep.footballmatch.R
import com.hidayatasep.footballmatch.detailmatch.DetailMatchActivity
import com.hidayatasep.footballmatch.detailteam.DetailTeamActivity
import com.hidayatasep.footballmatch.listmatch.ListMatchAdapter
import com.hidayatasep.footballmatch.teams.TeamsAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.toast


class SearchActivity : AppCompatActivity(), SearchActivityContract.View{

    override lateinit var presenter: SearchActivityContract.Presenter

    private var typeSearch: Int = TYPE_SEARCH_MATCH
    private var keyword: String = ""
    private lateinit var teams: MutableList<Team>
    private lateinit var mTeamAdapter: TeamsAdapter
    private lateinit var events: MutableList<Event>
    private lateinit var mEventAdapter: ListMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        typeSearch = intent.getIntExtra("search", TYPE_SEARCH_MATCH)
        val apiRepository = ApiRepository()
        val gson = Gson()
        presenter = SearchActivityPresenter(this, apiRepository, gson, typeSearch)

        swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        recycler_view.layoutManager = LinearLayoutManager(this)
        if (typeSearch == TYPE_SEARCH_MATCH) {
            searchEditText.hint = "Search Match..."
            events = mutableListOf()
            mEventAdapter = ListMatchAdapter(this, events)  {
                event: Event -> eventItemClicked(event)
            }
            recycler_view.adapter = mEventAdapter
        } else {
            searchEditText.hint = "Search Team..."
            teams = mutableListOf()
            mTeamAdapter = TeamsAdapter(this, teams)  {
                team: Team -> teamItemClicked(team)
            }
            recycler_view.adapter = mTeamAdapter
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isEmpty()) {
                    btnClose.visibility = GONE
                } else {
                    btnClose.visibility = VISIBLE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btnClose.setOnClickListener {
            searchEditText.text = null
        }

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideKeyboard()
                    keyword = searchEditText.text.toString().trim()
                    if (keyword.isEmpty()) {
                        toast("Keyword cannot be empty")
                    } else {
                        presenter.getSearchList(keyword)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showSearchEventList(data: List<Event>?) {
        events.clear()
        if (data == null) {
            showEmptyText(true)
            return
        } else {
            events.addAll(data)
            showEmptyText(data.isEmpty())
        }
        mEventAdapter.notifyDataSetChanged()
    }

    override fun showSearchTeamList(data: List<Team>?) {
        teams.clear()
        if (data == null) {
            showEmptyText(true)
            return
        } else {
            teams.addAll(data)
            showEmptyText(data.isEmpty())
        }
        mTeamAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun dissmissLoading() {
        swipeRefresh.isRefreshing = false
    }

    private fun eventItemClicked(event: Event) {
        val intent = Intent(this, DetailMatchActivity::class.java)
        intent.putExtra("event", event)
        startActivity(intent)
    }

    private fun teamItemClicked(team: Team) {
        val intent = Intent(this, DetailTeamActivity::class.java)
        intent.putExtra("team", team)
        startActivity(intent)
    }

    private fun showEmptyText(show: Boolean) {
        if (show) {
            tvEmpty.visibility = VISIBLE
            recycler_view.visibility = GONE
        } else {
            tvEmpty.visibility = GONE
            recycler_view.visibility = VISIBLE
        }
    }

    fun hideKeyboard() {
        searchEditText.clearFocus()
        val `in` = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }

    companion object {
        const val TYPE_SEARCH_MATCH = 1
        const val TYPE_SEARCH_TEAM = 2
    }

}