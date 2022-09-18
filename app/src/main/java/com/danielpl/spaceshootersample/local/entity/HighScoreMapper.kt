package com.danielpl.spaceshootersample.local.entity

import com.danielpl.spaceshootersample.repository.HighScore

fun HighScoreEntity.toHighScore(): HighScore {
    return HighScore(
        playerName = playerName,
        highScore = newScore
    )
}

fun HighScore.toHighScoreEntity(): HighScoreEntity {
    return HighScoreEntity(
        playerName = playerName,
        newScore = highScore
    )
}