package com.danielpl.spaceshootersample.repository

import com.danielpl.spaceshootersample.local.HighScoreDao
import com.danielpl.spaceshootersample.local.entity.HighScoreEntity
import com.danielpl.spaceshootersample.local.entity.toHighScore
import com.danielpl.spaceshootersample.local.entity.toHighScoreEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HighScoreImpl(
    private val dao: HighScoreDao
): HighScoreRepository{
    override fun insertHighScore(highScore: HighScore) {
        dao.insertHighScore(highScore.toHighScoreEntity())
    }

    override fun deleteHighScore(highScore: HighScore) {
        dao.deleteHighScore(highScore.toHighScoreEntity())
    }

    override fun getHighScores(): Flow<List<HighScore>> {
        return dao.getHighScores()
                .map { entities->
                        entities.map {
                            it.toHighScore()
                            }
                        }
    }
}