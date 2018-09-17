package app.helper

/**
 * Created by hidayatasep43 on 9/17/2018.
 * hidayatasep43@gmail.com
 */
object Utils {

    fun replaceSemiColonToEnter(text: String?): String {
        if(text == null) return ""
        return text.replace(";", "\n")
    }

}
