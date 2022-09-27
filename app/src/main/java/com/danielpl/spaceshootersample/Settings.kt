package com.danielpl.spaceshootersample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.danielpl.spaceshootersample.preferences.Preferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Settings : AppCompatActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
    }

    override fun onResume() {
        super.onResume()

        val playerNameEditText = findViewById<EditText>(R.id.playerNameEditText)
        playerNameEditText.setText(preferences.getPlayerName())

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            preferences.savePlayerName(playerNameEditText.text.toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}