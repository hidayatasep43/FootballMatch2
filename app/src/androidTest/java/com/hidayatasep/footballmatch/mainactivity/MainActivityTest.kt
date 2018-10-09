package com.hidayatasep.footballmatch.mainactivity

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.hidayatasep.footballmatch.MainActivity
import com.hidayatasep.footballmatch.R
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    var idlingResources: MainIdlingResources? = null

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        idlingResources = MainIdlingResources(5000)
        Espresso.registerIdlingResources(idlingResources)
    }

    @Test
    fun mainActivityTest() {
        val recyclerView = onView(Matchers.allOf(withId(R.id.recycler_view), isDisplayed()))
        recyclerView.perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        recyclerView.perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        onView(withId(R.id.image_club_home)).check(matches(isDisplayed()))
        onView(withId(R.id.image_club_away)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_club_home)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_club_away)).check(matches(isDisplayed()))
        onView(withText("Goals"))
                .check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        Espresso.unregisterIdlingResources(idlingResources)
    }
}
