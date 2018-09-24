package com.hidayatasep.footballmatch.listmatch

import app.data.Event
import com.hidayatasep.footballmatch.base.BasePresenter
import com.hidayatasep.footballmatch.base.BaseView

/**
 * Created by hidayatasep43 on 9/16/2018.
 * hidayatasep43@gmail.com
 */
interface ListMatchView: BaseView<BasePresenter>{

    fun showLoading()
    fun dissmissLoading()
    fun showTeamList(data: List<Event>)

}