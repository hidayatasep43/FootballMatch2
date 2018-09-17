package app.data

import com.google.gson.annotations.SerializedName

/**
 * Created by hidayatasep43 on 9/16/2018.
 * hidayatasep43@gmail.com
 */
data class Team(
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null
)