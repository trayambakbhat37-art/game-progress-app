package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.model.GameProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameProgressDao {
    @Query("SELECT * FROM game_progress ORDER BY isCurrentActive DESC, gameTitle ASC")
    fun getAllGameProgress(): Flow<List<GameProgressEntity>>

    @Query("SELECT * FROM game_progress WHERE isCurrentActive = 1 LIMIT 1")
    fun getCurrentActiveGame(): Flow<GameProgressEntity?>

    @Query("SELECT * FROM game_progress WHERE gameId = :gameId LIMIT 1")
    suspend fun getGameById(gameId: String): GameProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<GameProgressEntity>)

    @Query("UPDATE game_progress SET isCurrentActive = 0")
    suspend fun resetAllActive()

    @Query("UPDATE game_progress SET isCurrentActive = 1 WHERE gameId = :gameId")
    suspend fun markActive(gameId: String)

    @Transaction
    suspend fun setActiveGame(gameId: String) {
        resetAllActive()
        markActive(gameId)
    }

    @Query("UPDATE game_progress SET currentStage = :stage WHERE gameId = :gameId")
    suspend fun updateCurrentStage(gameId: String, stage: String)

    @Query("UPDATE game_progress SET clearedCount = :cleared WHERE gameId = :gameId")
    suspend fun updateClearedCount(gameId: String, cleared: Int)

    @Query("UPDATE game_progress SET isFavorite = :isFavorite WHERE gameId = :gameId")
    suspend fun updateFavorite(gameId: String, isFavorite: Boolean)

    @Query("SELECT COUNT(*) FROM game_progress")
    suspend fun getCount(): Int
}
