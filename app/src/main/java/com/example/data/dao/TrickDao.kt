package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.GameTrickEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrickDao {
    @Query("SELECT * FROM tricks ORDER BY upvotes DESC, timestamp DESC")
    fun getAllTricks(): Flow<List<GameTrickEntity>>

    @Query("SELECT * FROM tricks WHERE id = :id")
    fun getTrickById(id: Long): Flow<GameTrickEntity?>

    @Query("SELECT * FROM tricks WHERE isSaved = 1 ORDER BY timestamp DESC")
    fun getSavedTricks(): Flow<List<GameTrickEntity>>

    @Query("SELECT * FROM tricks WHERE gameId = :gameId ORDER BY upvotes DESC")
    fun getTricksByGame(gameId: String): Flow<List<GameTrickEntity>>

    @Query("SELECT * FROM tricks WHERE category = :category ORDER BY upvotes DESC")
    fun getTricksByCategory(category: String): Flow<List<GameTrickEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrick(trick: GameTrickEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tricks: List<GameTrickEntity>)

    @Update
    suspend fun updateTrick(trick: GameTrickEntity)

    @Query("UPDATE tricks SET isUpvoted = :isUpvoted, upvotes = :upvotes WHERE id = :id")
    suspend fun updateUpvote(id: Long, isUpvoted: Boolean, upvotes: Int)

    @Query("UPDATE tricks SET isSaved = :isSaved WHERE id = :id")
    suspend fun updateSaved(id: Long, isSaved: Boolean)

    @Query("UPDATE tricks SET isLevelCleared = :isCleared WHERE id = :id")
    suspend fun updateLevelCleared(id: Long, isCleared: Boolean)

    @Query("UPDATE tricks SET commentsCount = commentsCount + 1 WHERE id = :id")
    suspend fun incrementCommentsCount(id: Long)

    @Query("DELETE FROM tricks WHERE id = :id")
    suspend fun deleteTrick(id: Long)

    @Query("SELECT COUNT(*) FROM tricks")
    suspend fun getCount(): Int
}
