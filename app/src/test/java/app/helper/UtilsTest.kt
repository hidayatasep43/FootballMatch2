package app.helper

import app.helper.Utils.replaceKgText
import app.helper.Utils.replaceMeterText
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

    @Test
    fun testReplaceKgText(){
        val text = "74,8Kg"
        val result = "74,8"
        assertEquals(result, replaceKgText(text))
    }

    @Test
    fun testReplaceMeterText(){
        val text = "190 m (6 ft 3 in)"
        val result = "190"
        assertEquals(result, replaceMeterText(text))
    }

}