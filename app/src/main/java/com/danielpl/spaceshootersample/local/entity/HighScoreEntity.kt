package com.danielpl.spaceshootersample.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HighScoreEntity(
    val playerName: String,
    val newScore: Int,
    @PrimaryKey val id: Int? = null
)
