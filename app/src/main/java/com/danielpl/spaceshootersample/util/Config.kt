package com.danielpl.spaceshootersample.util

import android.os.SystemClock.uptimeMillis
import kotlin.random.Random

class Config {
    val STAGE_WIDTH = 1080
    val STAGE_HEIGHT = 720
    val STAR_COUNT = 40
    val ENEMY_COUNT = 10
    val RNG = Random(uptimeMillis())
    val PLAYER_HEIGHT = 75
    val ACCELERATION = 1.1f
    val MIN_VEL = 0.1f
    val MAX_VEL = 20f
    val GRAVITY = 1.1f
    val LIFT = -(GRAVITY*2f)
    val DRAG = 0.97f
    val PLAYER_STARTING_HEALTH = 3
    val PLAYER_STARTING_POSITION = 10f
    @Volatile var isBoosting = false
    var playerSpeed = 0f
}