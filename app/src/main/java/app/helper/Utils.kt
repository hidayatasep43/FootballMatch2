package app.helper

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by hidayatasep43 on 9/17/2018.
 * hidayatasep43@gmail.com
 */
object Utils {

    fun replaceSemiColonToEnter(text: String?): String {
        if(text == null) return ""
        return text.replace(";", "\n")
    }

    fun convertEventTimeToGMT(dateEvent: String?, strTime: String?) : String?{
        val dateTime = dateEvent + " " + strTime
        val dateFormatInput = SimpleDateFormat("yyyy-MM-dd HH:mm:ssz",
                Locale.getDefault())
        val dateFormatOutput = SimpleDateFormat("EEE, d MMM yyyy'\n'HH:mm",
                Locale.getDefault())
        try {
            val tempFormat = dateFormatInput.parse(dateTime)
            return dateFormatOutput.format(tempFormat)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateEvent
    }

}
