package com.danielpl.spaceshootersample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.danielpl.spaceshootersample.preferences.Preferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GameActivity: AppCompatActivity() {

    @Inject lateinit var game: Game
    private val TAG = "GameActivity"
    //private lateinit var game: Game
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //game = Game()
        setContentView(game)
        Log.d(TAG, "onCreate called")
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