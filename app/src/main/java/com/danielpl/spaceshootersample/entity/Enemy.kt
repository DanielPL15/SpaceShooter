package com.danielpl.spaceshootersample.entity

import android.content.res.Resources
import com.danielpl.spaceshootersample.R
import com.danielpl.spaceshootersample.util.Config
import com.danielpl.spaceshootersample.util.Config.STAGE_HEIGHT
import com.danielpl.spaceshootersample.util.Config.playerSpeed
import com.danielpl.spaceshootersample.util.MovementShapes
import java.lang.Math.round
import java.lang.Math.sin
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class Enemy(res: Resources): BitmapEntity() {

    init {
        respawn()

        var id = R.drawable.tm_1
        when(Config.RNG.nextInt(1,6)){
            1-> id = R.drawable.tm_1
            2-> id = R.drawable.tm_2
            3-> id = R.drawable.tm_3
            4-> id = R.drawable.tm_4
            5-> id = R.drawable.tm_5
        }
        val bmp = loadBitmap(res, id, Config.ENEMY_HEIGHT)
        setSprite(flipVertically(bmp))
    }

    override fun respawn(){

        val shapeRandomizer = Config.RNG.nextInt(1,3)
        if(shapeRandomizer==1){
            movementShape = MovementShapes.SIN

            heightModifier = Config.RNG.nextDouble(0.20,0.80).toFloat()
            velXModifier = Config.RNG.nextDouble(0.5, 3.0).toFloat()
        } else{
            movementShape = MovementShapes.STRAIGHT
        }
        x = (Config.STAGE_WIDTH + Config.RNG.nextInt(Config.ENEMY_SPAWN_OFFSET)).toFloat()
        y = Config.RNG.nextInt(Config.STAGE_HEIGHT - Config.ENEMY_HEIGHT).toFloat()

    }
    override fun update() {
        when(movementShape){
            MovementShapes.SIN->{
                velX = -playerSpeed/velXModifier
                x += velX
                val modifiedHeight = heightModifier* STAGE_HEIGHT/2
                y = kotlin.math.round(kotlin.math.sin(x/250) * modifiedHeight + (STAGE_HEIGHT/2))
            }
            MovementShapes.STRAIGHT->{
                velX = -playerSpeed
                x += velX
            }
        }
        if(right()<0){
            respawn()
        }
    }

    override fun onCollision(that: Entity) {
        respawn()
    }
}