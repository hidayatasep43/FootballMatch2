package com.hidayatasep.footballmatch.listfavorite

import app.data.Event
import com.hidayatasep.footballmatch.base.BasePresenter
import com.hidayatasep.footballmatch.base.BaseView

/**
 * Created by hidayatasep43 on 10/4/2018.
 * hidayatasep43@gmail.com
 */
interface ListFavoriteContract {

    interface View : BaseView<Presenter> {
        fun showEventFavoriteList(data: List<Event>)
    }

    interface Presenter : BasePresenter {}
}
