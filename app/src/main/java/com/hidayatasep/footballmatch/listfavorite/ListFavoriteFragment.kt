package com.hidayatasep.footballmatch.listfavorite


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.data.Event
import app.data.Team
import com.hidayatasep.footballmatch.R.color.colorAccent
import com.hidayatasep.footballmatch.detailmatch.DetailMatchActivity
import com.hidayatasep.footballmatch.detailteam.DetailTeamActivity
import com.hidayatasep.footballmatch.listmatch.ListMatchAdapter
import com.hidayatasep.footballmatch.teams.TeamsAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout


private const val ARG_PARAM_TYPE_LIST = "param"

class ListFavoriteFragment : Fragment(), ListFavoriteContract.View, AnkoComponent<Context> {

    override lateinit var presenter: ListFavoriteContract.Presenter

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterMatch: ListMatchAdapter
    private lateinit var mAdapterTeam: TeamsAdapter
    private lateinit var events: MutableList<Event>
    private lateinit var teams: MutableList<Team>
    private var typeList: Int = ListFavoriteMainFragment.TYPE_FAVORITE_MATCH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeList = it.getInt(ARG_PARAM_TYPE_LIST)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return createView(AnkoContext.create(ctx))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (typeList == ListFavoriteMainFragment.TYPE_FAVORITE_MATCH) {
            events = mutableListOf()
            mAdapterMatch = ListMatchAdapter(context as FragmentActivity, events) { event: Event ->
                eventItemClicked(event)
            }
            mRecyclerView.adapter = mAdapterMatch
        } else {
            teams = mutableListOf()
            mAdapterTeam = TeamsAdapter(context as FragmentActivity, teams) { team:Team ->
                teamItemClicked(team)
            }
            mRecyclerView.adapter = mAdapterTeam
        }
        mSwipeRefreshLayout.onRefresh {
            presenter.start()
        }
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

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

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showLoading() {
        mSwipeRefreshLayout.isRefreshing = true
    }

    override fun dissmissLoading() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun showEventFavoriteList(data: List<Event>) {
        mSwipeRefreshLayout.isRefreshing = false
        events.clear()
        events.addAll(data)
        mAdapterMatch.notifyDataSetChanged()
    }

    override fun showTeamFavoriteList(data: List<Team>) {
        mSwipeRefreshLayout.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        mAdapterTeam.notifyDataSetChanged()
    }

    private fun eventItemClicked(event: Event) {
        val intent = Intent(context, DetailMatchActivity::class.java)
        intent.putExtra("event", event)
        startActivity(intent)
    }

    private fun teamItemClicked(team: Team) {
        val intent = Intent(context, DetailTeamActivity::class.java)
        intent.putExtra("team", team)
        startActivity(intent)
    }

    companion object {

        @JvmStatic
        fun newInstance(typeList: Int) =
                ListFavoriteFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM_TYPE_LIST, typeList)
                    }
                }
    }
}
