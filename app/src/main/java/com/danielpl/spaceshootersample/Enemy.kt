package com.danielpl.spaceshootersample

import android.content.res.Resources

const val ENEMY_HEIGHT = 60
const val ENEMY_SPAWN_OFFSET = STAGE_WIDTH*2
class Enemy(res: Resources): BitmapEntity() {

    init {
        respawn()

        var id = R.drawable.tm_1
        when(RNG.nextInt(1,6)){
            1-> id = R.drawable.tm_1
            2-> id = R.drawable.tm_2
            3-> id = R.drawable.tm_3
            4-> id = R.drawable.tm_4
            5-> id = R.drawable.tm_5
        }
        val bmp = loadBitmap(res, id, ENEMY_HEIGHT)
        setSprite(flipVertically(bmp))
    }

    override fun respawn(){
        x = (STAGE_WIDTH + RNG.nextInt(ENEMY_SPAWN_OFFSET)).toFloat()
        y = RNG.nextInt(STAGE_HEIGHT- ENEMY_HEIGHT).toFloat()
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