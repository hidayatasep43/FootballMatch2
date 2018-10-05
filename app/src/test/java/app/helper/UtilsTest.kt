package app.helper

import app.helper.Utils.replaceSemiColonToEnter
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by hidayatasep43 on 9/28/2018.
 * hidayatasep43@gmail.com
 */
class UtilsTest {

    @Test
    fun testReplaceSemiColonTOEnter(){
        val text = "Asep;Hidayat"
        val result = "Asep\nHidayat"
        assertEquals(result, replaceSemiColonToEnter(text))
    }

}