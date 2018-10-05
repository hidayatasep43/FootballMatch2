package com.hidayatasep.footballmatch.listmatch

import android.util.Log
import app.data.Team
import app.helper.CoroutineContextProvider
import app.helper.LocalPreferences
import app.webservice.EventResponse
import app.webservice.TeamResponse
import com.google.gson.Gson
import com.hidayatasep.footballmatch.mainactivity.MainActivity
import com.hidayatasep.latihan2.ApiRepository
import com.hidayatasep.latihan2.TheSportDBApi
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by hidayatasep43 on 9/16/2018.
 * hidayatasep43@gmail.com
 */
class ListMatchPresenter (val view: ListMatchContract.View,
                          val apiRepository: ApiRepository,
                          val gson: Gson,
                          val localPreferences: LocalPreferences,
                          val typeList: Int,
                          val context: CoroutineContextProvider = CoroutineContextProvider()
                          ) : ListMatchContract.Presenter{

    init {
        view.presenter = this
        getTeam()
    }

    override fun start() {
        getEventsList("4328")
    }

    override fun getEventsList(idLeaguage: String?) {
        view.showLoading()
        if (typeList == MainActivity.TYPE_LIST_PREV) {
            async(context.main) {
                val data = bg {
                    gson.fromJson(apiRepository
                            .doRequest(TheSportDBApi.getPrevMatch(idLeaguage)),
                            EventResponse::class.java)
                }
                view.showTeamList(data.await().events)
                view.dissmissLoading()
            }
        } else {
            async(context.main) {
                val data = bg {
                    gson.fromJson(apiRepository
                            .doRequest(TheSportDBApi.getNextMatch(idLeaguage)),
                            EventResponse::class.java)
                }
                view.showTeamList(data.await().events)
                view.dissmissLoading()
            }
        }
    }

    private fun getTeam() {
        val isAlreadyGetTeam = localPreferences.getBoolean("update_team", false)
        if (isAlreadyGetTeam == null) {
            return
        }
        if (!isAlreadyGetTeam) {
            async(UI) {
                val data = bg {
                    gson.fromJson(apiRepository
                            .doRequest(TheSportDBApi.getTeams("English%20Premier%20League")),
                            TeamResponse::class.java)
                }
                for (team: Team in data.await().teams) {
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