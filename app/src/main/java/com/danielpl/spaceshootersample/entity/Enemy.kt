package com.danielpl.spaceshootersample.entity

import android.content.res.Resources
import com.danielpl.spaceshootersample.R
import com.danielpl.spaceshootersample.util.Config
import com.danielpl.spaceshootersample.util.Config.playerSpeed


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
        x = (Config.STAGE_WIDTH + Config.RNG.nextInt(Config.ENEMY_SPAWN_OFFSET)).toFloat()
        y = Config.RNG.nextInt(Config.STAGE_HEIGHT - Config.ENEMY_HEIGHT).toFloat()
    }
    override fun update() {
        velX = -playerSpeed
        x += velX
        if(right()<0){
           respawn()
        }
    }

    override fun onCollision(that: Entity) {
        respawn()
    }
}