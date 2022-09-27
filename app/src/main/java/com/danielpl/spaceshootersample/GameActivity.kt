package com.danielpl.spaceshootersample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameActivity : AppCompatActivity() {

    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        game = Game(this)
        setContentView(game)
        Log.d(R.string.game_activity_tag.toString(), "onCreate called")
    }

    override fun onPause() {
        super.onPause()
        game.pause()
    }

    override fun onResume() {
        super.onResume()
        game.resume()
    }

}