package com.danielpl.spaceshootersample.entity

import android.content.res.Resources
import com.danielpl.spaceshootersample.R
import com.danielpl.spaceshootersample.util.Config.DIFFERENT_ENEMIES
import com.danielpl.spaceshootersample.util.Config.DIFFERENT_SHAPES
import com.danielpl.spaceshootersample.util.Config.ENEMY_HEIGHT
import com.danielpl.spaceshootersample.util.Config.ENEMY_SPAWN_OFFSET
import com.danielpl.spaceshootersample.util.Config.HEIGHT_MODIFIER_MAX
import com.danielpl.spaceshootersample.util.Config.HEIGHT_MODIFIER_MIN
import com.danielpl.spaceshootersample.util.Config.RNG
import com.danielpl.spaceshootersample.util.Config.SIN_MODIFIER
import com.danielpl.spaceshootersample.util.Config.STAGE_HEIGHT
import com.danielpl.spaceshootersample.util.Config.STAGE_WIDTH
import com.danielpl.spaceshootersample.util.Config.VELX_MODIFIER_MAX
import com.danielpl.spaceshootersample.util.Config.VELX_MODIFIER_MIN
import com.danielpl.spaceshootersample.util.Config.playerSpeed
import com.danielpl.spaceshootersample.util.MovementShapes


class Enemy(res: Resources) : BitmapEntity() {

    init {
        respawn()

        var id = R.drawable.tm_1
        when (RNG.nextInt(DIFFERENT_ENEMIES) + 1) {
            1 -> id = R.drawable.tm_1
            2 -> id = R.drawable.tm_2
            3 -> id = R.drawable.tm_3
            4 -> id = R.drawable.tm_4
            5 -> id = R.drawable.tm_5
        }
        val bmp = loadBitmap(res, id, ENEMY_HEIGHT)
        setSprite(flipVertically(bmp))
    }

    override fun respawn() {

        val shapeRandomizer = RNG.nextInt(DIFFERENT_SHAPES) + 1
        if (shapeRandomizer == 1) {
            movementShape = MovementShapes.SIN

            heightModifier = RNG.nextDouble(HEIGHT_MODIFIER_MIN, HEIGHT_MODIFIER_MAX).toFloat()
            velXModifier = RNG.nextDouble(VELX_MODIFIER_MIN, VELX_MODIFIER_MAX).toFloat()
        } else {
            movementShape = MovementShapes.STRAIGHT
        }
        x = (STAGE_WIDTH + RNG.nextInt(ENEMY_SPAWN_OFFSET)).toFloat()
        y = RNG.nextInt(STAGE_HEIGHT - ENEMY_HEIGHT).toFloat()

    }

    override fun update() {
        when (movementShape) {
            MovementShapes.SIN -> {
                velX = -playerSpeed / velXModifier
                x += velX
                val modifiedHeight = heightModifier * STAGE_HEIGHT / 2
                y =
                    kotlin.math.round(kotlin.math.sin(x / SIN_MODIFIER) * modifiedHeight + (STAGE_HEIGHT / 2))
            }
            MovementShapes.STRAIGHT -> {
                velX = -playerSpeed
                x += velX
            }
        }
        if (right() < 0) {
            respawn()
        }
    }

    override fun onCollision(that: Entity) {
        respawn()
    }
}