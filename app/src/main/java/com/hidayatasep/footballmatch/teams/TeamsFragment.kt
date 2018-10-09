package com.hidayatasep.footballmatch.teams

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import app.data.Team
import com.hidayatasep.footballmatch.R.array.league
import com.hidayatasep.footballmatch.R.color.colorAccent
import com.hidayatasep.footballmatch.detailteam.DetailTeamActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamsFragment : Fragment(), AnkoComponent<Context>, TeamsContract.View{

    override lateinit var presenter: TeamsContract.Presenter

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var mAdapter: TeamsAdapter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mSpinner: Spinner
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var leagueName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = createView(AnkoContext.create(ctx))

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.simple_spinner_dropdown_item, spinnerItems)
        mSpinner.adapter = spinnerAdapter
        mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = mSpinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        mAdapter = TeamsAdapter(ctx, teams)  {
            team: Team -> teamItemClicked(team)
        }
        mRecyclerView.adapter = mAdapter

        mSwipeRefreshLayout.onRefresh {
            presenter.getTeamList(leagueName)
        }

        return view
    }


    override fun createView(ui: AnkoContext<Context>): View  = with (ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(10)
            leftPadding = dip(16)
            rightPadding = dip(16)

            mSpinner = spinner ()
            mSwipeRefreshLayout = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                mRecyclerView = recyclerView {
                    lparams (width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                    bottomPadding = dip(16)
                    clipToPadding = false
                }
            }
        }
    }

    override fun showLoading() {
        mSwipeRefreshLayout.isRefreshing = true
    }

    override fun dissmissLoading() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun showTeamList(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        mAdapter.notifyDataSetChanged()
    }

    private fun teamItemClicked(team: Team) {
        val intent = Intent(context, DetailTeamActivity::class.java)
        intent.putExtra("team", team)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                TeamsFragment()
    }
}
