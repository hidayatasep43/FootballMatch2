package com.hidayatasep.footballmatch.detailteam

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import app.data.Team
import app.data.TeamContract
import app.data.database
import app.helper.LocalPreferences
import com.hidayatasep.footballclub.GlideApp
import com.hidayatasep.footballmatch.R
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar


class DetailTeamActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var team: Team
    private lateinit var mPagerAdapter: DetailTeamPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        appbar.addOnOffsetChangedListener(this)

        team = intent.getParcelableExtra<Team>("team")
        GlideApp.with(this)
                .load(team.teamBadge)
                .into(imageClub)
        tvClub.text = team.teamName
        tvFormerYear.text = team.teamFormedYear
        tvStadium.text = team.teamStadium

        val overviewFragment: TeamOverviewFragment = TeamOverviewFragment.newInstance(team.teamDescription)
        val teamPlayerFragment: TeamPlayerFragment = TeamPlayerFragment.newInstance("","")

        mPagerAdapter = DetailTeamPagerAdapter(supportFragmentManager, overviewFragment, teamPlayerFragment)
        viewpager.adapter = mPagerAdapter
        tabLayout.setupWithViewPager(viewpager)


        val localPreferences = LocalPreferences.getInstance(this)
        val imageClub = localPreferences.getString(team.teamId, "")
        if(imageClub.isNullOrEmpty()) {
            localPreferences.put(team.teamId, team.teamBadge!!)
        }

        favoriteState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
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
            R.id.add_to_favorite -> {
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
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
    }

    private fun favoriteState(){
        database.use {
            val result = select(TeamContract.TABLE_FAVORITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to team.teamId)
            val favorite = result.parseList(classParser<Team>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun removeFromFavorite(){
        try{
            database.use {
                delete(TeamContract.TABLE_FAVORITE_TEAM,
                        "(" + TeamContract.TEAM_ID + "={id})",
                        "id" to team.teamId
                )
            }
            snackbar(htab_maincontent, "Removed from favorite").show()
        }catch (e: SQLiteConstraintException){
            snackbar(htab_maincontent, e.localizedMessage).show()
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(TeamContract.TABLE_FAVORITE_TEAM,
                        TeamContract.TEAM_ID to team.teamId,
                        TeamContract.TEAM_NAME to team.teamName,
                        TeamContract.TEAM_BADGE to team.teamBadge,
                        TeamContract.TEAM_FORMED_YEAR to team.teamFormedYear,
                        TeamContract.TEAM_STADIUM to team.teamStadium,
                        TeamContract.TEAM_DESC to team.teamDescription)
            }
            snackbar(htab_maincontent, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(htab_maincontent, e.localizedMessage).show()
        }
    }


    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset == 0 || -verticalOffset + toolbar.height < collapsingToolbar.getHeight()) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
        } else {
            supportActionBar?.setDisplayShowTitleEnabled(true)
        }
        if (-verticalOffset <= toolbar.height) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
        } else {
            supportActionBar?.setDisplayShowTitleEnabled(true)
        }
    }

    class DetailTeamPagerAdapter (
            fragmentManager: FragmentManager,
            val overviewFragment: TeamOverviewFragment,
            val teamPlayerFragment: TeamPlayerFragment)
        : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment? {
            return if (position == 0) {
                overviewFragment
            } else {
                teamPlayerFragment
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) {
                "Overview"
            } else {
                "Player"
            }
        }
        override fun getCount(): Int = 2
    }

}
