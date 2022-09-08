package com.danielpl.spaceshootersample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.SystemClock.uptimeMillis
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.danielpl.spaceshootersample.entity.*
import com.danielpl.spaceshootersample.preferences.Preferences
import com.danielpl.spaceshootersample.util.Config
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random


val RNG = Random(uptimeMillis())

@Volatile var isBoosting = false
var playerSpeed = 0f

private const val TAG = "Game"

@AndroidEntryPoint
class Game(context: Context) : SurfaceView(context), Runnable, SurfaceHolder.Callback {

    @Inject lateinit var preferences: Preferences
    @Inject lateinit var config: Config
    private lateinit var gameThread: Thread
    @Volatile
    private var isRunning = false
    private var isGameOver = false
    private val jukebox = Jukebox(context.assets)
    private val player = Player(resources)
    private var entities = ArrayList<Entity>()
    private val paint = Paint()
    private var distanceTraveled = 0
    private var maxDistanceTraveled = 0

    init {

        holder.addCallback(this)
        holder.setFixedSize(config.STAGE_WIDTH, config.STAGE_HEIGHT)
        for (i in 0 until config.STAR_COUNT){
            entities.add(Star())
        }
        for (i in 0 until config.ENEMY_COUNT){
            entities.add(Enemy(resources))
        }
    }

    private fun restart() {
        for(entity in entities){
            entity.respawn()
        }
        player.respawn()
        distanceTraveled = 0
        maxDistanceTraveled = preferences.getLongestDistance()
        isGameOver = false
        //play sound effect
        //reset the score, maybe update the highScore table


    }

    override fun run() {
        while(isRunning){
            // We cannot reuse a Thread object
            update()
            render()
        }
    }

    private fun update() {
        Log.d(TAG, "update")
        player.update()
        for(entity in entities){
            entity.update()
        }
        distanceTraveled += playerSpeed.toInt()
        checkCollisions()
        checkGameOver()
    }

    private fun checkGameOver() {
        if(player.health<1){
            isGameOver = true
            if(distanceTraveled>maxDistanceTraveled){
                preferences.saveLongestDistance(distanceTraveled)
            }
        }
    }

    private fun checkCollisions() {
        for(i in config.STAR_COUNT until entities.size){
            val enemy = entities[i]
            if(isColliding(enemy,player)){
                enemy.onCollision(player)
                player.onCollision(enemy)
                jukebox.play(SFX.crash)
            }
        }
    }


    private fun render() {
        Log.d(TAG, "render")
        val canvas = acquireAndLockCanvas() ?: return
        canvas.drawColor(Color.BLUE)
        for(entity in entities){
            entity.render(canvas, paint)
        }
        player.render(canvas,paint)
        renderHud(canvas,paint)
        holder.unlockCanvasAndPost(canvas)
    }

    private fun renderHud(canvas: Canvas, paint: Paint) {
        val textSize = 48f
        val margin = 10f
        paint.color = Color.WHITE
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = textSize

        if(!isGameOver){
            canvas.drawText("Health: ${player.health}", margin,textSize,paint)
            canvas.drawText("Distance: $distanceTraveled", margin,textSize*2,paint)
        }else{
            paint.textAlign = Paint.Align.CENTER
            val centerX = config.STAGE_WIDTH*0.5f
            val centerY = config.STAGE_HEIGHT*0.5f
            canvas.drawText("GAME OVER!", centerX,centerY,paint)
            canvas.drawText("press to restart", centerX,centerY+textSize,paint)
        }
    }

    private fun acquireAndLockCanvas() : Canvas?{
        if(holder?.surface?.isValid == false){
            return null
        }
       return holder.lockCanvas()
    }

    fun pause() {
        Log.d(TAG, "pause")
        isRunning = false
        try {
            gameThread.join()
        }
        catch (e: Exception){
            Log.d(TAG, "Error while pausing: $e")
        }
    }

    fun resume() {
        Log.d(TAG, "resume")
        gameThread = Thread(this)
        isRunning = true
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        Log.d(TAG, "surfaceCreated")
        gameThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(TAG, "surfaceChanged, width: $width, height: $height")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        Log.d(TAG, "surfaceDestroyed")
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "slowing down")
                isBoosting = false
            }
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "isBoosting")
                if(isGameOver){
                    restart()
                } else {
                    isBoosting = true
                }
            }
        }
        return true
    }
}