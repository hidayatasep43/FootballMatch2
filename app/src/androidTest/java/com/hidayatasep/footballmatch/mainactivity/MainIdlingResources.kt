package com.hidayatasep.footballmatch.mainactivity

import android.support.test.espresso.IdlingResource

/**
 * Created by hidayatasep43 on 10/3/2018.
 * hidayatasep43@gmail.com
 */
class MainIdlingResources(private val waitingTime: Long) : IdlingResource {

    private val startTime: Long
    private var resourceCallback: IdlingResource.ResourceCallback?= null

    init {
        this.startTime = System.currentTimeMillis()
    }

    override fun getName(): String {
        return MainActivity::class.java.name + ":" + waitingTime
    }

    override fun isIdleNow(): Boolean {
        val elapsed = System.currentTimeMillis() - startTime
        val idle = elapsed >= waitingTime
        if (idle) {
            resourceCallback!!.onTransitionToIdle()
        }
        return idle    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback
    }

}