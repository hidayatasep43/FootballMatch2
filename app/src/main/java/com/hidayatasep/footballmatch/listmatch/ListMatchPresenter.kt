package com.hidayatasep.footballmatch.listmatch

import android.util.Log
import app.data.Team
import app.helper.LocalPreferences
import app.webservice.EventResponse
import app.webservice.TeamResponse
import com.google.gson.Gson
import com.hidayatasep.footballmatch.base.BasePresenter
import com.hidayatasep.footballmatch.mainactivity.MainActivity
import com.hidayatasep.latihan2.ApiRepository
import com.hidayatasep.latihan2.TheSportDBApi
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by hidayatasep43 on 9/16/2018.
 * hidayatasep43@gmail.com
 */
class ListMatchPresenter (private val view: ListMatchView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val localPreferences: LocalPreferences,
                          private val typeList: Int) : BasePresenter{

    init {
        view.setPresenter(this)
        getTeam()
    }

    override fun start() {
        getEventsList("4328")
    }

    fun getEventsList(idLeaguage: String?) {
        view.showLoading()
        if (typeList == MainActivity.TYPE_LIST_PREV) {
            async(UI) {
                val data = bg {
                    gson.fromJson(apiRepository
                            .doRequest(TheSportDBApi.getPrevMatch(idLeaguage)),
                            EventResponse::class.java)
                }
                view.showTeamList(data.await().events)
                view.dissmissLoading()
            }
        } else {
            doAsync {
                val data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextMatch(idLeaguage)),
                        EventResponse::class.java)

                uiThread {
                    view.dissmissLoading()
                    view.showTeamList(data.events)
                }
            }
        }
    }

    private fun getTeam() {
        val isAlreadyGetTeam = localPreferences.getBoolean("update_team", false)
        if (isAlreadyGetTeam == null) {
            return
        }
        if (!isAlreadyGetTeam) {
            doAsync {
                val data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeams("English Premier League")),
                        TeamResponse::class.java)

                uiThread {
                    for (team: Team in data.teams) {
                        if(team.teamId != null && team.teamBadge != null) {
                            localPreferences.put(team.teamId!!, team.teamBadge!!)
                        }
                        Log.d("LIST_MATCH_PRESENTER", team.toString())
                    }
                    localPreferences.put("update_team", true)
                }
            }
        }

    }

}