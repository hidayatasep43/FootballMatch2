package app.helper

import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by hidayatasep43 on 10/5/2018.
 * hidayatasep43@gmail.com
 */
class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}