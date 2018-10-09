package com.hidayatasep.footballmatch.listfavorite

import android.content.Context
import app.data.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * Created by hidayatasep43 on 9/23/2018.
 * hidayatasep43@gmail.com
 */
class ListFavoritePresenter(
        val context: Context,
        val view: ListFavoriteContract.View,
        val typeFavoriteList: Int)
    : ListFavoriteContract.Presenter {

    init {
        view.presenter  = this
    }

    override fun start() {
        if (typeFavoriteList == ListFavoriteMainFragment.TYPE_FAVORITE_MATCH) {
            getFavoriteMatch()
        } else {
            getFavoriteTeam()
        }

    }

    private fun getFavoriteMatch() {
        context.database.use {
            view.showLoading()
            val result = select(FavoriteEventContract.TABLE_FAVORITE_EVENT)
            val favorite = result.parseList(classParser<Event>())
            view.dissmissLoading()
            view.showEventFavoriteList(favorite)
        }
    }

    private fun getFavoriteTeam() {
        context.database.use {
            view.showLoading()
            val result = select(TeamContract.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<Team>())
            view.dissmissLoading()
            view.showTeamFavoriteList(favorite)
        }
    }



}