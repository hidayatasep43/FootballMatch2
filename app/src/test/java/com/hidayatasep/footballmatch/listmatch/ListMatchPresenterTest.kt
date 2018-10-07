package com.hidayatasep.footballmatch.listmatch

import app.data.Event
import app.helper.LocalPreferences
import app.helper.TestContextProvider
import app.webservice.EventResponse
import com.google.gson.Gson
import com.hidayatasep.footballmatch.mainactivity.MainActivity
import com.hidayatasep.latihan2.ApiRepository
import com.hidayatasep.latihan2.TheSportDBApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by hidayatasep43 on 9/28/2018.
 * hidayatasep43@gmail.com
 */
class ListMatchPresenterTest {

    @Mock private lateinit var view: ListMatchContract.View

    @Mock private lateinit var gson: Gson

    @Mock private lateinit var apiRepository: ApiRepository

    @Mock private lateinit var localPreferences: LocalPreferences

    private lateinit var presenter: ListMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        presenter = ListMatchPresenter(view, apiRepository, gson, localPreferences, MainActivity.TYPE_LIST_PREV)

        `when`(view.isActive).thenReturn(true)
    }

    @Test fun createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        presenter = ListMatchPresenter(view, apiRepository, gson, localPreferences, MainActivity.TYPE_LIST_PREV, TestContextProvider())

        // Then the presenter is set to the view
        verify(view).presenter = presenter
    }

   @Test
    fun tesGetEventsList() {
        presenter = ListMatchPresenter(view, apiRepository, gson, localPreferences, MainActivity.TYPE_LIST_PREV, TestContextProvider())

        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val idLeague = "4328"
        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPrevMatch(idLeague)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getEventsList(idLeague)
        verify(view).showLoading()
        verify(view).showEventList(events)
        verify(view).dissmissLoading()
    }
}