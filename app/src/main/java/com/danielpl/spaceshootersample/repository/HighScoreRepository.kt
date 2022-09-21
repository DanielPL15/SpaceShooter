package com.danielpl.spaceshootersample.repository

import kotlinx.coroutines.flow.Flow

interface HighScoreRepository {
    suspend fun insertHighScore(highScore: HighScore)

    suspend fun deleteHighScore(highScore: HighScore)

    fun getHighScores(): Flow<List<HighScore>>

    fun getLongestDistance(): Flow<HighScore>
}