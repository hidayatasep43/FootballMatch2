package com.hidayatasep.footballmatch.teams

import app.helper.CoroutineContextProvider
import app.webservice.ApiRepository
import app.webservice.TeamResponse
import com.google.gson.Gson
import com.hidayatasep.latihan2.TheSportDBApi
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by hidayatasep43 on 10/7/2018.
 * hidayatasep43@gmail.com
 */
class TeamsPresenter (val view: TeamsContract.View,
                      val apiRepository: ApiRepository,
                      val gson: Gson,
                      val context: CoroutineContextProvider = CoroutineContextProvider())
    : TeamsContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {

    }

    override fun getTeamList(leaguage: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeams(leaguage)),
                        TeamResponse::class.java)
            }
            if (isActive) {
                view.showTeamList(data.await().teams)
                view.dissmissLoading()
            }
        }
    }


}