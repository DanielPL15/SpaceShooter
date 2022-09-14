package com.danielpl.spaceshootersample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.danielpl.spaceshootersample.preferences.Preferences
import com.danielpl.spaceshootersample.util.Jukebox
import com.danielpl.spaceshootersample.util.SFX
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.startGameButton)?.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val longestDistance = preferences.getLongestDistance()
        val highScore = findViewById<TextView>(R.id.highscore)
        highScore.text = getString(R.string.highScore, longestDistance.toString())
    }
}