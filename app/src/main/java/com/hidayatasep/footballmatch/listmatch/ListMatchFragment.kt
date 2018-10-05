package com.hidayatasep.footballmatch.listmatch


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
import com.hidayatasep.footballmatch.R
import com.hidayatasep.footballmatch.detailmatch.DetailMatchActivity

class ListMatchFragment : Fragment(), ListMatchContract.View {

    override lateinit var presenter: ListMatchContract.Presenter

    override var isActive: Boolean = false
        get() = isAdded


    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ListMatchAdapter
    private var events: MutableList<Event> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_list_match, container, false)

        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh)
        mSwipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = ListMatchAdapter(context as FragmentActivity, events) { event: Event ->
            eventItemClicked(event)
        }
        mRecyclerView.adapter = mAdapter

        mSwipeRefreshLayout.setOnRefreshListener {
            presenter.start()
        }
        presenter.start()

        return view
    }

    private fun eventItemClicked(event: Event) {
        val intent = Intent(context, DetailMatchActivity::class.java)
        intent.putExtra("event", event)
        startActivity(intent)
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


    companion object {
        @JvmStatic
        fun newInstance() =
                ListMatchFragment()
    }


}
