package com.hidayatasep.footballmatch.listfavorite

import android.content.Context
import app.data.Event
import app.data.FavoriteEventContract
import app.data.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * Created by hidayatasep43 on 9/23/2018.
 * hidayatasep43@gmail.com
 */
class ListFavoritePresenter(
        val context: Context,
        val view: ListFavoriteContract.View ) : ListFavoriteContract.Presenter {

    init {
        view.presenter  = this
    }

    override fun start() {
        getFavoriteTeam()
    }

    private fun getFavoriteTeam() {
        context.database.use {
            view.showLoading()
            val result = select(FavoriteEventContract.TABLE_FAVORITE_EVENT)
            val favorite = result.parseList(classParser<Event>())
            view.dissmissLoading()
            view.showTeamList(favorite)
        }
    }

}