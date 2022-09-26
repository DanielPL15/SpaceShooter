package com.danielpl.spaceshootersample.local

import androidx.room.*
import com.danielpl.spaceshootersample.local.entity.HighScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HighScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHighScore(highScoreEntity: HighScoreEntity)

    @Delete
    suspend fun deleteHighScore(highScoreEntity: HighScoreEntity)

    @Query(
        """
            SELECT *
            FROM highscoreentity
            ORDER BY newScore DESC
            LIMIT 4;
        """
    )
    fun get4HighestScores(): Flow<List<HighScoreEntity>>

    @Query(
        """
            SELECT *
            FROM highscoreentity
            WHERE newScore = 
             (SELECT MAX(newScore)
             FROM highscoreentity
            );
        """
    )
    fun getLongestDistance(): Flow<HighScoreEntity>

}