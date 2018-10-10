package app.webservice

import java.net.URL

/**
 * Created by hidayatasep43 on 9/15/2018.
 * hidayatasep43@gmail.com
 */
class ApiRepository {

    fun doRequest(url: String): String {
        return URL(url).readText()
    }

}