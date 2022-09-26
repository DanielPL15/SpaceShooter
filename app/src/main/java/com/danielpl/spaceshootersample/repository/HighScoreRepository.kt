package com.danielpl.spaceshootersample.repository

import kotlinx.coroutines.flow.Flow

interface HighScoreRepository {
    suspend fun insertHighScore(highScore: HighScore)

    suspend fun deleteHighScore(highScore: HighScore)

    fun get4HighestScores(): Flow<List<HighScore>>

    fun getLongestDistance(): Flow<HighScore>
}