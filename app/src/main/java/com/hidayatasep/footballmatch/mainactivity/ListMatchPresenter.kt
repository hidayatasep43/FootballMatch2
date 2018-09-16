package com.hidayatasep.footballmatch.mainactivity

import app.data.EventResponse
import com.google.gson.Gson
import com.hidayatasep.latihan2.ApiRepository
import com.hidayatasep.latihan2.TheSportDBApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by hidayatasep43 on 9/16/2018.
 * hidayatasep43@gmail.com
 */
class ListMatchPresenter (private val view: ListMatchView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val typeList: Int) {

    init {
        view.setPresenter(this)
    }

    fun getEventsList(idLeaguage: String?) {
        view.showLoading()
        if (typeList == MainActivity.TYPE_LIST_PREV) {
            doAsync {
                val data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPrevMatch(idLeaguage)),
                        EventResponse::class.java)

                uiThread {
                    view.dissmissLoading()
                    view.showTeamList(data.events)
                }
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

}