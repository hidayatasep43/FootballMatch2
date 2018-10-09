package com.hidayatasep.footballmatch.detailteam


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
import android.widget.LinearLayout
import app.data.Player
import app.webservice.PlayersResponse
import com.google.gson.Gson
import com.hidayatasep.footballmatch.R
import com.hidayatasep.footballmatch.detailplayer.DetailPlayerActivity
import com.hidayatasep.latihan2.ApiRepository
import com.hidayatasep.latihan2.TheSportDBApi
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

private const val ARG_PARAM = "param"

class TeamPlayerFragment : Fragment(), AnkoComponent<Context> {

    private lateinit var teamId: String
    private var players: MutableList<Player> = mutableListOf()
    private lateinit var mAdapter: PlayerAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private var gson:Gson = Gson()
    private var apiRepository:ApiRepository = ApiRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            teamId = it.getString(ARG_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = createView(AnkoContext.create(ctx))

        mAdapter = PlayerAdapter(ctx, players)  {
            player: Player -> playerItemClicked(player)
        }
        mRecyclerView.adapter = mAdapter
        mSwipeRefreshLayout.onRefresh {
            getPlayersTeam()
        }
        getPlayersTeam()

        return view
    }


    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            leftPadding = dip(4)
            rightPadding = dip(4)

            mSwipeRefreshLayout = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                mRecyclerView = recyclerView {
                    lparams (width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                    bottomPadding = dip(16)
                    topPadding = dip(16)
                    clipToPadding = false
                }
            }
        }
    }

    private fun getPlayersTeam() {
        mSwipeRefreshLayout.isRefreshing = true
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPlayersTeam(teamId)),
                    PlayersResponse::class.java
            )

            uiThread {
                mSwipeRefreshLayout.isRefreshing = false
                players.clear()
                players.addAll(data.player)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun playerItemClicked(player: Player) {
        val intent = Intent(context, DetailPlayerActivity::class.java)
        intent.putExtra("player", player)
        startActivity(intent)
    }

    companion object {

        @JvmStatic
        fun newInstance(teamId: String) =
                TeamPlayerFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM, teamId)
                    }
                }
    }
}
