package com.hidayatasep.footballmatch.listmatch

import app.data.Event
import com.hidayatasep.footballmatch.base.BasePresenter
import com.hidayatasep.footballmatch.base.BaseView

/**
 * Created by hidayatasep43 on 10/4/2018.
 * hidayatasep43@gmail.com
 */
interface ListMatchContract {

    interface View : BaseView<Presenter> {
        var isActive: Boolean
        fun showEventList(data: List<Event>)
    }

    interface Presenter : BasePresenter {
        fun getEventsList(idLeaguage: String?)
    }

}