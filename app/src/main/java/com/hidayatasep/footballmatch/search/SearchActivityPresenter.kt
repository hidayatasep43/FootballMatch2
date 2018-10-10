package com.hidayatasep.footballmatch.search

import app.webservice.ApiRepository
import app.webservice.EventSearchResponse
import app.webservice.TeamResponse
import com.google.gson.Gson
import com.hidayatasep.latihan2.TheSportDBApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class SearchActivityPresenter (val view: SearchActivityContract.View,
                               val apiRepository: ApiRepository,
                               val gson: Gson,
                               val typeSearch: Int)
    : SearchActivityContract.Presenter {

    override fun getSearchList(strSearch: String?) {
        view.showLoading()
        if (typeSearch == SearchActivity.TYPE_SEARCH_MATCH) {
            doAsync {
                val data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getMatchSearch(strSearch)),
                        EventSearchResponse::class.java
                )
                uiThread {
                    view.dissmissLoading()
                    view.showSearchEventList(data.event)
                }
            }
        } else {
            doAsync {
                val data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamSearch(strSearch)),
                        TeamResponse::class.java
                )
                uiThread {
                    view.dissmissLoading()
                    view.showSearchTeamList(data.teams)
                }
            }
        }
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}