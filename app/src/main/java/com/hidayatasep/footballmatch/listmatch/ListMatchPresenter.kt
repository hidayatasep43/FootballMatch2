package com.hidayatasep.footballmatch.listmatch

import app.helper.CoroutineContextProvider
import app.webservice.ApiRepository
import app.webservice.EventResponse
import com.google.gson.Gson
import com.hidayatasep.latihan2.TheSportDBApi
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by hidayatasep43 on 9/16/2018.
 * hidayatasep43@gmail.com
 */
class ListMatchPresenter (val view: ListMatchContract.View,
                          val apiRepository: ApiRepository,
                          val gson: Gson,
                          val typeList: Int,
                          val context: CoroutineContextProvider = CoroutineContextProvider()
                          ) : ListMatchContract.Presenter{

    init {
        view.presenter = this
    }

    override fun start() {}

    override fun getEventsList(idLeaguage: String?) {
        view.showLoading()
        if (typeList == ListMatchMainFragment.TYPE_LIST_PREV) {
            async(context.main) {
                val data = bg {
                    gson.fromJson(apiRepository
                            .doRequest(TheSportDBApi.getPrevMatch(idLeaguage)),
                            EventResponse::class.java)
                }
                if (isActive) {
                    view.showEventList(data.await().events)
                    view.dissmissLoading()
                }
            }
        } else {
            async(context.main) {
                val data = bg {
                    gson.fromJson(apiRepository
                            .doRequest(TheSportDBApi.getNextMatch(idLeaguage)),
                            EventResponse::class.java)
                }
                if(isActive) {
                    view.showEventList(data.await().events)
                    view.dissmissLoading()
                }
            }
        }
    }

    override fun getTypeMatch(): Int {
        return typeList
    }

}