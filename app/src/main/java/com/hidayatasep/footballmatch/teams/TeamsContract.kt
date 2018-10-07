package com.hidayatasep.footballmatch.teams

import app.data.Team
import com.hidayatasep.footballmatch.base.BasePresenter
import com.hidayatasep.footballmatch.base.BaseView

/**
 * Created by hidayatasep43 on 10/7/2018.
 * hidayatasep43@gmail.com
 */
interface TeamsContract {

    interface View : BaseView<Presenter> {
        fun showTeamList(data: List<Team>)
    }

    interface Presenter : BasePresenter {
        fun getTeamList(leaguage: String?)
    }

}