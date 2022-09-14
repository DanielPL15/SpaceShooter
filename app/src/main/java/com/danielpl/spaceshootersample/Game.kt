package com.danielpl.spaceshootersample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.danielpl.spaceshootersample.entity.*
import com.danielpl.spaceshootersample.preferences.Preferences
import com.danielpl.spaceshootersample.util.Config
import com.danielpl.spaceshootersample.util.Config.isBoosting
import com.danielpl.spaceshootersample.util.Config.playerSpeed
import com.danielpl.spaceshootersample.util.Jukebox
import com.danielpl.spaceshootersample.util.SFX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.TickerMode
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate


@AndroidEntryPoint
class Game(context: Context) : SurfaceView(context), Runnable, SurfaceHolder.Callback {

    @Inject lateinit var preferences: Preferences
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
    private var isBlinking = false
    private var blinkingCounter = System.currentTimeMillis()

    init {

        holder.addCallback(this)
        holder.setFixedSize(Config.STAGE_WIDTH, Config.STAGE_HEIGHT)
        for (i in 0 until Config.STAR_COUNT){
            entities.add(Star())
        }
        for (i in 0 until Config.ENEMY_COUNT){
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
        jukebox.play(SFX.starting)
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
        Log.d(R.string.game_tag.toString(), "update")
        player.update()
        for(entity in entities){
            entity.update()
        }
        distanceTraveled += playerSpeed.toInt()
        if(!isBlinking) {
            checkCollisions()
        }
        checkGameOver()
    }

    private fun checkGameOver() {
        if(player.health<1){
            if(!isGameOver){
                jukebox.play(SFX.death)
            }
            isGameOver = true
            if(distanceTraveled>maxDistanceTraveled){
                preferences.saveLongestDistance(distanceTraveled)
            }
        }
    }

    private fun checkCollisions() {
        for(i in Config.STAR_COUNT until entities.size){
            val enemy = entities[i]
            if(isColliding(enemy,player)){
                enemy.onCollision(player)
                player.onCollision(enemy)
                jukebox.play(SFX.crash)
                isBlinking = true
                blinkingCounter = System.currentTimeMillis()
                // During the "delay" the player will not lose lives
                Timer("isBlinking")
                    .schedule(3000){
                        isBlinking = false
                }
            }
        }
    }


    private fun render() {
        Log.d(R.string.game_tag.toString(), "render")
        val canvas = acquireAndLockCanvas() ?: return
        canvas.drawColor(Color.BLACK)
        //canvas.drawBitmap(loadBitmap(resources,R.drawable.startmenu,Config.STAGE_HEIGHT),0f,0f,paint)
        for(entity in entities){
            entity.render(canvas, paint)
        }
        if(!isBlinking) {
            player.render(canvas, paint)
        } else if(System.currentTimeMillis()-blinkingCounter<100){
            player.render(canvas,paint)
        } else{
            Timer("resetBlinkingCounter")
                .schedule(100){
                    blinkingCounter = System.currentTimeMillis()
                }
        }
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
            canvas.drawText(context.getString(R.string.health_counter, player.health.toString()), margin,textSize,paint)
            canvas.drawText(context.getString(R.string.distance_counter, distanceTraveled.toString()), margin,textSize*2,paint)
        }else{
            paint.textAlign = Paint.Align.CENTER
            val centerX = Config.STAGE_WIDTH*0.5f
            val centerY = Config.STAGE_HEIGHT*0.5f
            canvas.drawText(context.getString(R.string.game_over), centerX,centerY,paint)
            canvas.drawText(context.getString(R.string.restart), centerX,centerY+textSize,paint)
        }
    }

    private fun acquireAndLockCanvas() : Canvas?{
        if(holder?.surface?.isValid == false){
            return null
        }
       return holder.lockCanvas()
    }

    fun pause() {
        Log.d(R.string.game_tag.toString(), "pause")
        isRunning = false
        try {
            gameThread.join()
        }
        catch (e: Exception){
            Log.d(R.string.game_tag.toString(), "Error while pausing: $e")
        }
    }

    fun resume() {
        Log.d(R.string.game_tag.toString(), "resume")
        gameThread = Thread(this)
        isRunning = true
        jukebox.play(SFX.starting)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        Log.d(R.string.game_tag.toString(), "surfaceCreated")
        gameThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(R.string.game_tag.toString(), "surfaceChanged, width: $width, height: $height")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        Log.d(R.string.game_tag.toString(), "surfaceDestroyed")
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> {
                Log.d(R.string.game_tag.toString(), "slowing down")
                isBoosting = false
            }
            MotionEvent.ACTION_DOWN -> {
                Log.d(R.string.game_tag.toString(), "isBoosting")
                if(isGameOver){
                    restart()
                } else {
                    jukebox.play(SFX.boost)
                    isBoosting = true
                }
            }
        }
        return true
    }
}