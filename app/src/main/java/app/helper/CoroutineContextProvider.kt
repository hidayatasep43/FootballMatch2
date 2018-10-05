package app.helper

import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by hidayatasep43 on 10/5/2018.
 * hidayatasep43@gmail.com
 */
open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { UI }
}