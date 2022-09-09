package com.danielpl.spaceshootersample.entity

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.danielpl.spaceshootersample.util.Config
import com.danielpl.spaceshootersample.util.Config.playerSpeed

class Star: Entity() {
    private val color = Color.YELLOW
    private val radius: Float = (Config.RNG.nextInt(6)+2).toFloat()

    init {
        respawn()
    }

    override fun respawn() {
        x = Config.RNG.nextInt(Config.STAGE_WIDTH).toFloat()
        y = Config.RNG.nextInt(Config.STAGE_HEIGHT).toFloat()
        width = radius * 2f
        height = width
    }

    override fun update() {
        super.update()
        x+= -playerSpeed
        if(right()<0){
            setLeft(Config.STAGE_WIDTH.toFloat())
            setTop(Config.RNG.nextInt(Config.STAGE_HEIGHT -height.toInt()).toFloat())
        }
        if(top() > Config.STAGE_HEIGHT) setBottom(0f)
    }

    override fun render(canvas: Canvas, paint: Paint) {
        super.render(canvas, paint)
        paint.color = color
        canvas.drawCircle(x,y,radius, paint)
    }
}