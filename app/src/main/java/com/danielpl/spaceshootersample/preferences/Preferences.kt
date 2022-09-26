package com.danielpl.spaceshootersample.preferences

interface Preferences {

    fun saveLongestDistance(distance: Int)

    fun getLongestDistance(): Int

    fun savePlayerName(playerName: String)

    fun getPlayerName(): String?

    companion object {
        const val LONGEST_DIST = "longest_distance"
        const val PLAYER_NAME = "player_name"

    }
}