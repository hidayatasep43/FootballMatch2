package com.hidayatasep.footballmatch.detailmatch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import app.data.Event
import com.hidayatasep.footballmatch.R

class DetailMatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)


        val event = intent.getParcelableExtra<Event>("event")
        Log.d("DetailMatchActivity", event.toString())
    }
}
