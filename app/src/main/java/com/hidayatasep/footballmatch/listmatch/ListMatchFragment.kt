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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import app.data.Event
import com.hidayatasep.footballmatch.R
import com.hidayatasep.footballmatch.detailmatch.DetailMatchActivity
import org.jetbrains.anko.support.v4.ctx

class ListMatchFragment : Fragment(), ListMatchContract.View {

    override lateinit var presenter: ListMatchContract.Presenter


    override var isActive: Boolean = false
        get() = isAdded

    val leagueIdList = arrayOf("4328", "4329", "4331", "4332", "4334", "4335")
    private var leagueId: String = "4328"

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ListMatchAdapter
    private lateinit var mSpinner: Spinner

    private var events: MutableList<Event> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_list_match, container, false)

        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh)
        mSwipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mSpinner = view.findViewById(R.id.spinner)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = ListMatchAdapter(context as FragmentActivity, events) { event: Event ->
            eventItemClicked(event)
        }
        mRecyclerView.adapter = mAdapter

        mSwipeRefreshLayout.setOnRefreshListener {
            presenter.getEventsList(leagueId)
        }

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        mSpinner.adapter = spinnerAdapter
        mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueId = leagueIdList[position]
                presenter.getEventsList(leagueId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

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

    override fun showEventList(data: List<Event>) {
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
