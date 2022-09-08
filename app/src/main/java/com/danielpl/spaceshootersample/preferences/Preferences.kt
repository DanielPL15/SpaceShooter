package com.danielpl.spaceshootersample.preferences

interface Preferences {

    fun saveLongestDistance(distance: Int)

    fun getLongestDistance(): Int

    companion object {
        const val LONGEST_DIST = "longest_distance"
    }
}