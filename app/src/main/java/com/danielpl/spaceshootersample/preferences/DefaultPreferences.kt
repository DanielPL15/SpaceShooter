package com.danielpl.spaceshootersample.preferences

import android.content.SharedPreferences
import com.danielpl.spaceshootersample.preferences.Preferences.Companion.LONGEST_DIST

class DefaultPreferences(
    private val sharedPref: SharedPreferences
): Preferences {
    override fun saveLongestDistance(distance: Int) {
        sharedPref.edit()
            .putInt(LONGEST_DIST, distance)
            .apply()
    }

    override fun getLongestDistance(): Int {
        return sharedPref.getInt(LONGEST_DIST,0)
    }
}