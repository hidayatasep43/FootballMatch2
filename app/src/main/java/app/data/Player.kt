package app.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by hidayatasep43 on 10/7/2018.
 * hidayatasep43@gmail.com
 */
@Parcelize
data class Player (

    @SerializedName("idPlayer")
    var playerId: String? = null,

    @SerializedName("strPlayer")
    var playerName: String? = null,

    @SerializedName("strThumb")
    var playerImage: String? = null,

    @SerializedName("strHeight")
    var playerHeight: String? = "-",

    @SerializedName("strWeight")
    var playerWeight: String? = "-",

    @SerializedName("strPosition")
    var playerPosition: String? = null,

    @SerializedName("strDescriptionEN")
    var playerDescription: String? = null
) : Parcelable