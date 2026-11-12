package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.CommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments WHERE trickId = :trickId ORDER BY timestamp ASC")
    fun getCommentsForTrick(trickId: Long): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<CommentEntity>)

    @Query("UPDATE comments SET likes = :likes, isLiked = :isLiked WHERE id = :id")
    suspend fun updateLike(id: Long, likes: Int, isLiked: Boolean)

    @Query("SELECT COUNT(*) FROM comments")
    suspend fun getCount(): Int
}
