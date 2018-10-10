package com.hidayatasep.footballmatch.search

import app.data.Event
import app.data.Team
import com.hidayatasep.footballmatch.base.BasePresenter
import com.hidayatasep.footballmatch.base.BaseView

interface SearchActivityContract {

    interface View : BaseView<Presenter> {
        fun showSearchEventList(data: List<Event> ?)
        fun showSearchTeamList(data: List<Team> ?)
    }

    interface Presenter : BasePresenter {
        fun getSearchList(strSearch: String? = "")
    }

}