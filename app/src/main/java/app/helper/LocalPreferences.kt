package app.helper

import android.content.Context
import android.content.SharedPreferences


class LocalPreferences private constructor(context: Context) {

    private val mPref: SharedPreferences
    private var mEditor: SharedPreferences.Editor? = null

    init {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)
    }

    fun put(key: String, value: String) {
        doEdit()
        mEditor!!.putString(key, value)
        doCommit()
    }

    fun put(key: String, value: Boolean) {
        doEdit()
        mEditor!!.putBoolean(key, value)
        doCommit()
    }

    fun getString(key: String, defaultValue: String): String? {
        return mPref.getString(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean? {
        return mPref.getBoolean(key, defaultValue)
    }

    private fun doEdit() {
        if (mEditor == null) {
            mEditor = mPref.edit()
        }
    }

    private fun doCommit() {
        if (mEditor != null) {
            mEditor!!.commit()
            mEditor = null
        }
    }

    companion object {

        private val SETTINGS_NAME = "com.hidayatasep.footballmatch.pref"
        private var sSharedPrefs: LocalPreferences? = null

        fun getInstance(context: Context): LocalPreferences {
            if (sSharedPrefs == null) {
                sSharedPrefs = LocalPreferences(context.applicationContext)
            }
            return sSharedPrefs as LocalPreferences
        }
    }
}

