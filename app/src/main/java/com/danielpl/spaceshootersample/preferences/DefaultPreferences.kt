package com.danielpl.spaceshootersample.preferences

import android.content.SharedPreferences
import com.danielpl.spaceshootersample.R
import com.danielpl.spaceshootersample.preferences.Preferences.Companion.LONGEST_DIST
import com.danielpl.spaceshootersample.preferences.Preferences.Companion.PLAYER_NAME

class DefaultPreferences(
    private val sharedPref: SharedPreferences
) : Preferences {
    override fun saveLongestDistance(distance: Int) {
        sharedPref.edit()
            .putInt(LONGEST_DIST, distance)
            .apply()
    }

    override fun getLongestDistance(): Int {
        return sharedPref.getInt(LONGEST_DIST, 0)
    }

    override fun savePlayerName(playerName: String) {
        sharedPref.edit()
            .putString(PLAYER_NAME, playerName)
            .apply()
    }

    override fun getPlayerName(): String? {
        return sharedPref.getString(PLAYER_NAME, R.string.player_name_example.toString())
    }
}