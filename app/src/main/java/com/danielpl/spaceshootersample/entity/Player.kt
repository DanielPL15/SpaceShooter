package com.danielpl.spaceshootersample.entity

import android.content.res.Resources
import android.util.Log
import androidx.core.math.MathUtils.clamp
import com.danielpl.spaceshootersample.R
import com.danielpl.spaceshootersample.util.Config
import com.danielpl.spaceshootersample.util.Config.isBoosting
import com.danielpl.spaceshootersample.util.Config.playerSpeed

class Player(res: Resources) : BitmapEntity() {
    var health = 0

    init {
        setSprite(loadBitmap(res, R.drawable.player_ship, Config.PLAYER_HEIGHT))
        health = Config.PLAYER_STARTING_HEALTH
    }

    override fun respawn() {
        health = Config.PLAYER_STARTING_HEALTH
        x = (Config.PLAYER_STARTING_POSITION)
    }

    override fun onCollision(that: Entity) {
        Log.d(R.string.player_tag.toString(), "OnCollision")
        health--
    }

    override fun update() {
        velX *= Config.DRAG
        velY += Config.GRAVITY
        if (isBoosting) {
            velX *= Config.ACCELERATION
            velY += Config.LIFT
        }

        velX = clamp(velX, Config.MIN_VEL, Config.MAX_VEL)
        velY = clamp(velY, -Config.MAX_VEL, Config.MAX_VEL)

        y += velY
        playerSpeed = velX

        if (bottom() > Config.STAGE_HEIGHT) {
            setBottom(Config.STAGE_HEIGHT.toFloat())
        } else if (top() < 0f) {
            setTop(0f)
        }

    }

}