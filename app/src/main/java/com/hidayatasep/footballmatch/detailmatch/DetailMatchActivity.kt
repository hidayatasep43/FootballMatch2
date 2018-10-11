package com.hidayatasep.footballmatch.detailmatch

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import app.data.Event
import app.data.FavoriteEventContract
import app.data.database
import app.helper.Utils
import app.webservice.ApiRepository
import app.webservice.TeamResponse
import com.google.gson.Gson
import com.hidayatasep.footballclub.GlideApp
import com.hidayatasep.footballmatch.R
import com.hidayatasep.footballmatch.R.drawable.ic_add_to_favorites
import com.hidayatasep.footballmatch.R.drawable.ic_added_to_favorites
import com.hidayatasep.footballmatch.R.id.add_to_favorite
import com.hidayatasep.footballmatch.R.menu.detail_menu
import com.hidayatasep.latihan2.TheSportDBApi
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailMatchActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var event: Event
    private val request = ApiRepository()
    private val gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        event = intent.getParcelableExtra<Event>("event")

        tv_time_match.text = Utils.convertEventTimeToGMT(event.dateEvent, event.strTime)
        tv_score_home.text = event.homeScore
        tv_score_away.text = event.awayScore

        setImageTeam(event.idHomeTeam, image_club_home)
        setImageTeam(event.idAwayTeam, image_club_away)
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

        favoriteState()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteEventContract.TABLE_FAVORITE_EVENT)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to event.idEvent)
            val favorite = result.parseList(classParser<Event>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun removeFromFavorite(){
        try{
        database.use {
                delete(FavoriteEventContract.TABLE_FAVORITE_EVENT,
                        "(" + FavoriteEventContract.EVENT_ID + "={event_id})",
                        "event_id" to event.idEvent
                )
            }
            snackbar(nestedScrolView, "Removed to favorite").show()
        }catch (e: SQLiteConstraintException){
            snackbar(nestedScrolView, e.localizedMessage).show()
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoriteEventContract.TABLE_FAVORITE_EVENT,
                        FavoriteEventContract.EVENT_ID to event.idEvent,
                        FavoriteEventContract.LEAGUE_NAME to event.leagueName,
                        FavoriteEventContract.HOME_TEAM_NAME to event.homeTeam,
                        FavoriteEventContract.AWAY_TEAM_NAME to event.awayTeam,
                        FavoriteEventContract.HOME_SCORE to event.homeScore,
                        FavoriteEventContract.AWAY_SCORE to event.awayScore,
                        FavoriteEventContract.HOME_GOAL_DETAILS to event.homeGoalDetails,
                        FavoriteEventContract.HOME_RED_CARD to event.homeRedCard,
                        FavoriteEventContract.HOME_YELLOW_CARD to event.homeYellowCard,
                        FavoriteEventContract.HOME_LINEUP_GOAL_KEEPER to event.homeLineupGoalKeeper,
                        FavoriteEventContract.HOME_LINEUP_DEFENSE to event.homeLineupDefense,
                        FavoriteEventContract.HOME_LINEUP_MIDFIELD to event.homeLineupMidfield,
                        FavoriteEventContract.HOME_LINEUP_FORWARD to event.homeLineupForward,
                        FavoriteEventContract.HOME_LINEUP_SUBTITUES to event.homeLineupSubtitues,
                        FavoriteEventContract.HOME_FORMATION to event.homeFormation,
                        FavoriteEventContract.AWAY_GOAL_DETAILS to event.awayGoalDetails,
                        FavoriteEventContract.AWAY_RED_CARD to event.awayRedCard,
                        FavoriteEventContract.AWAY_YELLOW_CARD to event.awayYellowCard,
                        FavoriteEventContract.AWAY_LINEUP_GOAL_KEEPER to event.awayLineupGoalkeeper,
                        FavoriteEventContract.AWAY_LINEUP_DEFENSE to event.awayLineupDefense,
                        FavoriteEventContract.AWAY_LINEUP_MIDFIELD to event.awayLineupMidfield,
                        FavoriteEventContract.AWAY_LINEUP_FORWARD to event.awayLineupForward,
                        FavoriteEventContract.AWAY_LINEUP_SUBSTITUES to event.awayLineupSubstitutes,
                        FavoriteEventContract.AWAY_FORMATION to event.awayFormation,
                        FavoriteEventContract.HOME_SHOTS to event.homeShots,
                        FavoriteEventContract.AWAY_SHOTS to event.awayShots,
                        FavoriteEventContract.DATE_EVENT to event.dateEvent,
                        FavoriteEventContract.DATE_EVENT_STR to event.strDateEvent,
                        FavoriteEventContract.TIME_STR to event.strTime,
                        FavoriteEventContract.HOME_TEAM_ID to event.idHomeTeam,
                        FavoriteEventContract.AWAY_TEAM_ID to event.idAwayTeam)
            }
            snackbar(nestedScrolView, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(nestedScrolView, e.localizedMessage).show()
        }
    }

    private fun setImageTeam(idTeam: String?, imageView: ImageView) {
        doAsync {
            val data = gson.fromJson(request
                    .doRequest(TheSportDBApi.getTeamsById(idTeam)),
                    TeamResponse::class.java
            )

            uiThread {
                if (!isDestroyed) {
                    GlideApp.with(imageView.context)
                            .load(data.teams[0].teamBadge)
                            .into(imageView)
                }
            }
        }
    }

}
