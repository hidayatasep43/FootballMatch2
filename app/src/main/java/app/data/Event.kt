package app.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by hidayatasep43 on 9/16/2018.
 * hidayatasep43@gmail.com
 */
@Parcelize
data class Event(

        @SerializedName("idEvent")
        var idEvent: String,

        @SerializedName("strLeague")
        var leagueName: String? = null,

        @SerializedName("strHomeTeam")
        var homeTeam: String? = null,

        @SerializedName("strAwayTeam")
        var awayTeam: String? = null,

        @SerializedName("intHomeScore")
        var homeScore: String? = null,

        @SerializedName("intAwayScore")
        var awayScore: String? = null,

        @SerializedName("strHomeGoalDetails")
        var homeGoalDetails: String? = null,

        @SerializedName("strHomeRedCards")
        var homeRedCard: String? = null,

        @SerializedName("strHomeYellowCards")
        var homeYellowCard: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var homeLineupGoalKeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        var homeLineupDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var homeLineupMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var homeLineupForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var homeLineupSubtitues: String? = null,

        @SerializedName("strHomeFormation")
        var homeFormation: String? = null,

        @SerializedName("strAwayGoalDetails")
        var awayGoalDetails: String? = null,

        @SerializedName("strAwayRedCards")
        var awayRedCard: String? = null,

        @SerializedName("strAwayYellowCards")
        var awayYellowCard: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var awayLineupGoalkeeper: String? = null,

        @SerializedName("strAwayLineupDefense")
        var awayLineupDefense: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var awayLineupMidfield: String? = null,

        @SerializedName("strAwayLineupForward")
        var awayLineupForward: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var awayLineupSubstitutes: String? = null,

        @SerializedName("strAwayFormation")
        var awayFormation: String? = null,

        @SerializedName("intHomeShots")
        var homeShots: String? = null,

        @SerializedName("intAwayShots")
        var awayShots: String? = null,

        @SerializedName("dateEvent")
        var dateEvent: String? = null,

        @SerializedName("strDate")
        var strDateEvent: String? = null,

        @SerializedName("strTime")
        var strTime: String? = null,

        @SerializedName("idHomeTeam")
        var idHomeTeam: String,

        @SerializedName("idAwayTeam")
        var idAwayTeam: String
) : Parcelable