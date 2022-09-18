package com.danielpl.spaceshootersample.repository

import kotlinx.coroutines.flow.Flow

interface HighScoreRepository {
    fun insertHighScore(highScore: HighScore)

    fun deleteHighScore(highScore: HighScore)

    fun getHighScores(): Flow<List<HighScore>>
}