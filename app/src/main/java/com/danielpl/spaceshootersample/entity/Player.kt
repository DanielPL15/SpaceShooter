package com.danielpl.spaceshootersample.entity

import android.content.res.Resources
import android.util.Log
import androidx.core.math.MathUtils.clamp
import com.danielpl.spaceshootersample.R
import com.danielpl.spaceshootersample.isBoosting
import com.danielpl.spaceshootersample.playerSpeed
import com.danielpl.spaceshootersample.util.Config
import javax.inject.Inject

class Player(res: Resources): BitmapEntity() {
    @Inject lateinit var config: Config
    private val TAG = "Player"
    var health = config.PLAYER_STARTING_HEALTH
    init {
        setSprite(loadBitmap(res, R.drawable.player_ship, config.PLAYER_HEIGHT))
    }

    override fun respawn() {
        health = config.PLAYER_STARTING_HEALTH
        x= (config.PLAYER_STARTING_POSITION.toFloat()).toFloat()
    }

    override fun onCollision(that: Entity) {
        Log.d(TAG, "OnCollision")
        health--
    }

    override fun update() {
        velX*= config.DRAG.toFloat()
        velY += config.GRAVITY.toFloat()
        if(isBoosting){
            velX *= config.ACCELERATION.toFloat()
            velY += config.LIFT.toFloat()
        }

        velX = clamp(velX, config.MIN_VEL.toFloat(), config.MAX_VEL.toFloat())
        velY = clamp(velY, -config.MAX_VEL.toFloat(), config.MAX_VEL.toFloat())

        y += velY
        playerSpeed = velX

        if(bottom()> config.STAGE_HEIGHT){
            setBottom(config.STAGE_HEIGHT.toFloat())
        } else if(top()<0f){
            setTop(0f)
        }

    }

}