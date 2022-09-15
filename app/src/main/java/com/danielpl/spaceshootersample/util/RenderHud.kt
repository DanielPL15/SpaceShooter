package com.danielpl.spaceshootersample.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.danielpl.spaceshootersample.R

class RenderHud(private val canvas: Canvas, private val paint: Paint, private val context: Context) {
    private val textSize = 48f
    private val margin = 10f

    init {
        paint.color = Color.WHITE
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = textSize
    }

    fun showHealthAndDistance(playerHealth: Int, distanceTraveled: Int){
        canvas.drawText(context.getString(R.string.health_counter, playerHealth.toString()), margin,textSize,paint)
        canvas.drawText(context.getString(R.string.distance_counter, distanceTraveled.toString()), margin,textSize*2,paint)
    }

    fun gameOver(){
        paint.textAlign = Paint.Align.CENTER
        val centerX = Config.STAGE_WIDTH*0.5f
        val centerY = Config.STAGE_HEIGHT*0.5f
        canvas.drawText(context.getString(R.string.game_over), centerX,centerY,paint)
        canvas.drawText(context.getString(R.string.restart), centerX,centerY+textSize,paint)
    }
}