package com.danielpl.spaceshootersample.entity

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.danielpl.spaceshootersample.R
import com.danielpl.spaceshootersample.RNG
import com.danielpl.spaceshootersample.playerSpeed

class Star: Entity() {
    private val TAG = "Star"
    private val color = Color.YELLOW
    private val radius: Float = (RNG.nextInt(6)+2).toFloat()

    init {
        respawn()
    }

    override fun respawn() {
        x = RNG.nextInt(R.integer.STAGE_WIDTH).toFloat()
        y = RNG.nextInt(R.integer.STAGE_HEIGHT).toFloat()
        width = radius * 2f
        height = width
    }

    override fun update() {
        super.update()
        x+= -playerSpeed
        if(right()<0){
            setLeft(R.integer.STAGE_WIDTH.toFloat())
            setTop(RNG.nextInt(R.integer.STAGE_HEIGHT -height.toInt()).toFloat())
        }
        if(top() > R.integer.STAGE_HEIGHT) setBottom(0f)
    }

    override fun render(canvas: Canvas, paint: Paint) {
        super.render(canvas, paint)
        paint.color = color
        canvas.drawCircle(x,y,radius, paint)
    }
}