package com.danielpl.spaceshootersample.entity

import android.content.res.Resources
import android.graphics.*

open class BitmapEntity : Entity() {
    private lateinit var bitmap: Bitmap

    fun setSprite(bmp: Bitmap) {
        bitmap = bmp
        width = bitmap.width.toFloat()
        height = bitmap.height.toFloat()
    }

    override fun render(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(bitmap, x, y, paint)
    }
}

fun loadBitmap(res: Resources, id: Int, height: Int): Bitmap {
    val bitmap = BitmapFactory.decodeResource(res, id)
    return scaleToTargetHeight(bitmap, height)
}

private val matrix = Matrix()
fun flip(src: Bitmap, horizontally: Boolean): Bitmap {
    matrix.reset()
    val cx = src.width / 2
    val cy = src.height / 2
    if (horizontally) {
        matrix.postScale(1f, -1f, cx.toFloat(), cy.toFloat())
    } else {
        matrix.postScale(-1f, 1f, cx.toFloat(), cy.toFloat())
    }
    return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
}

fun flipVertically(src: Bitmap) = flip(src, false)
// flipHorizontally is never used
// fun flipHorizontally(src: Bitmap) = flip(src, true)


fun scaleToTargetHeight(src: Bitmap, targetHeight: Int): Bitmap {
    val ratio = targetHeight / src.height.toFloat()
    val newH = (src.height * ratio).toInt()
    val newW = (src.width * ratio).toInt()
    return Bitmap.createScaledBitmap(src, newW, newH, true)
}