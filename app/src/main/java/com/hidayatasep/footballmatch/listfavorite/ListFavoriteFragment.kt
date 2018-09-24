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
import com.hidayatasep.footballmatch.R.color.colorAccent
import com.hidayatasep.footballmatch.base.BasePresenter
import com.hidayatasep.footballmatch.detailmatch.DetailMatchActivity
import com.hidayatasep.footballmatch.listmatch.ListMatchAdapter
import com.hidayatasep.footballmatch.listmatch.ListMatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class ListFavoriteFragment : Fragment(), ListMatchView,AnkoComponent<Context> {

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListFavoritePresenter: ListFavoritePresenter
    private lateinit var mAdapter: ListMatchAdapter
    private var events: MutableList<Event> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mAdapter = ListMatchAdapter(context as FragmentActivity, events) { event: Event ->
            eventItemClicked(event)
        }
        mRecyclerView.adapter = mAdapter
        mSwipeRefreshLayout.onRefresh {
            mListFavoritePresenter.start()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {



        return createView(AnkoContext.create(ctx))
    }

    private fun eventItemClicked(event: Event) {
        val intent = Intent(context, DetailMatchActivity::class.java)
        intent.putExtra("event", event)
        startActivity(intent)
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
                    bottomPadding = dip(70)
                    clipToPadding = false
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mListFavoritePresenter.start()
    }

    override fun showLoading() {
        mSwipeRefreshLayout.isRefreshing = true
    }

    override fun dissmissLoading() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun showTeamList(data: List<Event>) {
        mSwipeRefreshLayout.isRefreshing = false
        events.clear()
        events.addAll(data)
        mAdapter.notifyDataSetChanged()
    }

    override fun setPresenter(presenter: BasePresenter) {
        mListFavoritePresenter = presenter as ListFavoritePresenter
    }


    companion object {

        @JvmStatic
        fun newInstance() =
                ListFavoriteFragment()
    }
}
