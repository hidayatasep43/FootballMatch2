package com.hidayatasep.footballmatch.teams

import app.webservice.ApiRepository
import app.webservice.TeamResponse
import com.google.gson.Gson
import com.hidayatasep.latihan2.TheSportDBApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by hidayatasep43 on 10/7/2018.
 * hidayatasep43@gmail.com
 */
class TeamsPresenter (val view: TeamsContract.View,
                      val apiRepository: ApiRepository,
                      val gson: Gson)
    : TeamsContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {

    }

    override fun getTeamList(leaguage: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeams(leaguage)),
                    TeamResponse::class.java
            )

            uiThread {
                view.dissmissLoading()
                view.showTeamList(data.teams)
            }
        }
    }


}