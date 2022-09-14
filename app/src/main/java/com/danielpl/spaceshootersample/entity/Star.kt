package com.danielpl.spaceshootersample.entity

import android.graphics.Canvas
import android.graphics.Color.rgb
import android.graphics.Paint
import android.os.Build
import com.danielpl.spaceshootersample.util.Config
import com.danielpl.spaceshootersample.util.Config.playerSpeed

class Star: Entity() {
    private val r = (Config.RNG.nextInt(256)).toFloat()
    private val g = (Config.RNG.nextInt(256)).toFloat()
    private val b = (Config.RNG.nextInt(256)).toFloat()
    private var color: Int = 0
    private val radius: Float = (Config.RNG.nextInt(10)+2).toFloat()
    private lateinit var shape: String
    private val rx: Float = (Config.RNG.nextInt(10)+2).toFloat()
    private val ry: Float = (Config.RNG.nextInt(10)+2).toFloat()
    private val ovalRandomizer = (Config.RNG.nextFloat())

    init {
        respawn()
    }

    override fun respawn() {
        x = Config.RNG.nextInt(Config.STAGE_WIDTH).toFloat()
        y = Config.RNG.nextInt(Config.STAGE_HEIGHT).toFloat()
        width = radius * 2f
        height = width
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Star will have a random color
            // Feature requires at least SDK 26
            color = rgb(r,g,b)
        }
        when((Config.RNG.nextInt(3))+1){
            1 -> shape = Shapes.CIRCLE.toString()
            2 -> shape = Shapes.RECTANGLE.toString()
            3 -> shape = Shapes.OVAL.toString()
        }
    }

    override fun update() {
        super.update()
        // Smaller stars are slower
        x+= -(playerSpeed * radius/100)
        if(right()<0){
            setLeft(Config.STAGE_WIDTH.toFloat())
            setTop(Config.RNG.nextInt(Config.STAGE_HEIGHT -height.toInt()).toFloat())
        }
        if(top() > Config.STAGE_HEIGHT) setBottom(0f)
    }

    override fun render(canvas: Canvas, paint: Paint) {
        super.render(canvas, paint)
        paint.color = color
        // Stars can be circles, rectangles or ovals
        when(shape){
            "CIRCLE" -> canvas.drawCircle(x,y,radius, paint)
            "RECTANGLE"-> canvas.drawRoundRect(
                x+radius,
                y+radius,
                x-radius,
                y-radius,
                rx,
                ry,
                paint
                )
            "OVAL"-> canvas.drawOval(
                x+(ovalRandomizer*radius),
                y+radius,
                x-(ovalRandomizer*radius),
                y-radius,
                paint)
        }
    }
}