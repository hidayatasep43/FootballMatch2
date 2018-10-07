package app.data

import com.google.gson.annotations.SerializedName

/**
 * Created by hidayatasep43 on 10/7/2018.
 * hidayatasep43@gmail.com
 */
data class Player (

    @SerializedName("idPlayer")
    var playerId: String? = null,

    @SerializedName("strPlayer")
    var playerName: String? = null,

    @SerializedName("strThumb")
    var playerImage: String? = null,

    @SerializedName("strHeight")
    var playerHeight: String? = null,

    @SerializedName("strWeight")
    var playerWeight: String? = null,

    @SerializedName("strPosition")
    var playerPosition: String? = null,

    @SerializedName("strDescriptionEN")
    var playerDescription: String? = null
)