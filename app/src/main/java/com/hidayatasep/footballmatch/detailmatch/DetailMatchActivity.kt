package com.hidayatasep.footballmatch.detailmatch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.data.Event
import app.helper.LocalPreferences
import app.helper.Utils
import com.hidayatasep.footballclub.GlideApp
import com.hidayatasep.footballmatch.R
import kotlinx.android.synthetic.main.activity_detail_match.*

class DetailMatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)


        val event = intent.getParcelableExtra<Event>("event")
        //Log.d("DetailMatchActivity", event.toString())

        tv_time_match.text = event.strDateEvent
        tv_score_home.text = event.homeScore
        tv_score_away.text = event.awayScore

        val localPreferences = LocalPreferences.getInstance(this)
        val imageHome = localPreferences.getString(event.idHomeTeam, "")
        val imageAway = localPreferences.getString(event.idAwayTeam, "")
        GlideApp.with(this)
                .load(imageHome)
                .into(image_club_home)
        GlideApp.with(this)
                .load(imageAway)
                .into(image_club_away)

        tv_club_home.text = event.homeTeam
        tv_club_away.text = event.awayTeam
        tvDetailGoalsHome.text = Utils.replaceSemiColonToEnter(event.homeGoalDetails)
        tvDetailGoalsAway.text = Utils.replaceSemiColonToEnter(event.awayGoalDetails)
        tvShootHome.text = event.homeShots
        tvShootAway.text = event.awayShots
        tvDetailFormationHome.text = event.homeFormation
        tvDetailFormationAway.text = event.awayFormation
        tvGoalKeeperHome.text = event.homeLineupGoalKeeper
        tvGoalKeeperAway.text = event.awayLineupGoalkeeper
        tvDefendHome.text =  Utils.replaceSemiColonToEnter(event.homeLineupDefense)
        tvDefendAway.text =  Utils.replaceSemiColonToEnter(event.awayLineupDefense)
        tvMidfieldHome.text =  Utils.replaceSemiColonToEnter(event.homeLineupMidfield)
        tvMidfieldAway.text =  Utils.replaceSemiColonToEnter(event.awayLineupMidfield)
        tvForwardHome.text =  Utils.replaceSemiColonToEnter(event.homeLineupForward)
        tvForwardAway.text =  Utils.replaceSemiColonToEnter(event.awayLineupForward)
        tvSubstitutesHome.text =  Utils.replaceSemiColonToEnter(event.homeLineupSubtitues)
        tvSubstitutesAway.text =  Utils.replaceSemiColonToEnter(event.awayLineupSubstitutes)
    }
}
