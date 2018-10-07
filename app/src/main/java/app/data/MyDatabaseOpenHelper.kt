package app.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by hidayatasep43 on 9/23/2018.
 * hidayatasep43@gmail.com
 */
class MyDatabaseOpenHelper(context: Context): ManagedSQLiteOpenHelper(context,
        "FavoriteMatchEvent.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(context.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteEventContract.TABLE_FAVORITE_EVENT, true,
                FavoriteEventContract.EVENT_ID to TEXT + PRIMARY_KEY,
                FavoriteEventContract.LEAGUE_NAME to TEXT,
                FavoriteEventContract.HOME_TEAM_NAME to TEXT,
                FavoriteEventContract.AWAY_TEAM_NAME to TEXT,
                FavoriteEventContract.HOME_SCORE to TEXT,
                FavoriteEventContract.AWAY_SCORE to TEXT,
                FavoriteEventContract.HOME_GOAL_DETAILS to TEXT,
                FavoriteEventContract.HOME_RED_CARD to TEXT,
                FavoriteEventContract.HOME_YELLOW_CARD to TEXT,
                FavoriteEventContract.HOME_LINEUP_GOAL_KEEPER to TEXT,
                FavoriteEventContract.HOME_LINEUP_DEFENSE to TEXT,
                FavoriteEventContract.HOME_LINEUP_MIDFIELD to TEXT,
                FavoriteEventContract.HOME_LINEUP_FORWARD to TEXT,
                FavoriteEventContract.HOME_LINEUP_SUBTITUES to TEXT,
                FavoriteEventContract.HOME_FORMATION to TEXT,
                FavoriteEventContract.AWAY_GOAL_DETAILS to TEXT,
                FavoriteEventContract.AWAY_RED_CARD to TEXT,
                FavoriteEventContract.AWAY_YELLOW_CARD to TEXT,
                FavoriteEventContract.AWAY_LINEUP_GOAL_KEEPER to TEXT,
                FavoriteEventContract.AWAY_LINEUP_DEFENSE to TEXT,
                FavoriteEventContract.AWAY_LINEUP_MIDFIELD to TEXT,
                FavoriteEventContract.AWAY_LINEUP_FORWARD to TEXT,
                FavoriteEventContract.AWAY_LINEUP_SUBSTITUES to TEXT,
                FavoriteEventContract.AWAY_FORMATION to TEXT,
                FavoriteEventContract.HOME_SHOTS to TEXT,
                FavoriteEventContract.AWAY_SHOTS to TEXT,
                FavoriteEventContract.DATE_EVENT to TEXT,
                FavoriteEventContract.DATE_EVENT_STR to TEXT,
                FavoriteEventContract.TIME_STR to TEXT,
                FavoriteEventContract.HOME_TEAM_ID to TEXT,
                FavoriteEventContract.AWAY_TEAM_ID to TEXT)

        db.createTable(TeamContract.TABLE_FAVORITE_TEAM, true,
                TeamContract.TEAM_ID to TEXT + PRIMARY_KEY,
                TeamContract.TEAM_NAME to TEXT,
                TeamContract.TEAM_BADGE to TEXT,
                TeamContract.TEAM_FORMED_YEAR to TEXT,
                TeamContract.TEAM_STADIUM to TEXT,
                TeamContract.TEAM_DESC to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newInversion: Int) {
        db.dropTable(FavoriteEventContract.TABLE_FAVORITE_EVENT, true)
        db.dropTable(TeamContract.TABLE_FAVORITE_TEAM, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)
