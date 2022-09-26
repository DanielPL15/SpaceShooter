package com.danielpl.spaceshootersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.danielpl.spaceshootersample.repository.HighScoreRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListOfRecords : AppCompatActivity() {

    @Inject lateinit var repository: HighScoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_records)
    }

    override fun onResume() {
        super.onResume()
        val playerRecord1 = findViewById<TextView>(R.id.textRecordPlayerRow2)
        val playerRecord2 = findViewById<TextView>(R.id.textRecordPlayerRow3)
        val playerRecord3 = findViewById<TextView>(R.id.textRecordPlayerRow4)
        val playerRecord4 = findViewById<TextView>(R.id.textRecordPlayerRow5)

        val kmRecord1 = findViewById<TextView>(R.id.textRecordKmRow2)
        val kmRecord2 = findViewById<TextView>(R.id.textRecordKmRow3)
        val kmRecord3 = findViewById<TextView>(R.id.textRecordKmRow4)
        val kmRecord4 = findViewById<TextView>(R.id.textRecordKmRow5)


        lifecycleScope.launch {
            try {
                repository.get4HighestScores().collect {
                    playerRecord1.text = it[0].playerName
                    kmRecord1.text = it[0].highScore.toString()
                    playerRecord2.text = it[1].playerName
                    kmRecord2.text = it[1].highScore.toString()
                    playerRecord3.text = it[2].playerName
                    kmRecord3.text = it[2].highScore.toString()
                    playerRecord4.text = it[3].playerName
                    kmRecord4.text = it[3].highScore.toString()

                }
            } catch(error: Exception){
                Log.d("List of Records", error.toString())
            }
        }

    }
}