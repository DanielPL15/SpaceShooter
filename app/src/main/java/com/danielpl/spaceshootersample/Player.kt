package com.danielpl.spaceshootersample

import android.content.res.Resources
import android.util.Log
import androidx.core.math.MathUtils.clamp

const val PLAYER_HEIGHT = 75
const val ACCELERATION = 1.1f
const val MIN_VEL = 0.1f
const val MAX_VEL = 20f
const val GRAVITY = 1.1f
const val LIFT = -(GRAVITY*2f)
const val DRAG = 0.97f
const val PLAYER_STARTING_HEALTH = 3
const val PLAYER_STARTING_POSITION = 10f
class Player(res: Resources): BitmapEntity() {
    private val TAG = "Player"
    var health = PLAYER_STARTING_HEALTH
    init {
        setSprite(loadBitmap(res, R.drawable.player_ship, PLAYER_HEIGHT))
    }

    override fun respawn() {
        health = PLAYER_STARTING_HEALTH
        x= PLAYER_STARTING_POSITION
    }

    override fun onCollision(that: Entity) {
        Log.d(TAG, "OnCollision")
        health--
    }

    override fun update() {
        velX*= DRAG
        velY += GRAVITY
        if(isBoosting){
            velX *= ACCELERATION
            velY += LIFT
        }

        velX = clamp(velX, MIN_VEL, MAX_VEL)
        velY = clamp(velY, -MAX_VEL, MAX_VEL)

        y += velY
        playerSpeed = velX

        if(bottom()> R.integer.STAGE_HEIGHT){
            setBottom(R.integer.STAGE_HEIGHT.toFloat())
        } else if(top()<0f){
            setTop(0f)
        }

    }

}