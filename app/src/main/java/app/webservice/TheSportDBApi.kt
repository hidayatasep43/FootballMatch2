package com.hidayatasep.latihan2

import com.hidayatasep.footballmatch.BuildConfig

/**
 * Created by hidayatasep43 on 9/15/2018.
 * hidayatasep43@gmail.com
 */
object TheSportDBApi {

    //English Premier League
    fun getTeams(league: String?): String {
        /*return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("search_all_teams.php")
                .appendQueryParameter("l", league)
                .build()
                .toString()*/
        val url = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league
        return url
    }

    fun getPrevMatch(idLeague: String?): String {
        /*return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventspastleague.php")
                .appendQueryParameter("id", idLeague)
                .build()
                .toString()*/
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + idLeague
    }

    fun getNextMatch(idLeague: String?): String {
        /*return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventsnextleague.php")
                .appendQueryParameter("id", idLeague)
                .build()
                .toString()*/
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=" + idLeague
    }

}