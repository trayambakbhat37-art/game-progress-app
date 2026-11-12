package com.example.data.repository

import com.example.data.dao.CommentDao
import com.example.data.dao.GameProgressDao
import com.example.data.dao.TrickDao
import com.example.data.model.CommentEntity
import com.example.data.model.GameProgressEntity
import com.example.data.model.GameTrickEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class TrickRepository(
    private val trickDao: TrickDao,
    private val commentDao: CommentDao,
    private val gameProgressDao: GameProgressDao
) {
    val allTricks: Flow<List<GameTrickEntity>> = trickDao.getAllTricks()
    val savedTricks: Flow<List<GameTrickEntity>> = trickDao.getSavedTricks()
    val allGamesProgress: Flow<List<GameProgressEntity>> = gameProgressDao.getAllGameProgress()
    val currentActiveGame: Flow<GameProgressEntity?> = gameProgressDao.getCurrentActiveGame()

    fun getTrickById(id: Long): Flow<GameTrickEntity?> = trickDao.getTrickById(id)

    fun getTricksByGame(gameId: String): Flow<List<GameTrickEntity>> = trickDao.getTricksByGame(gameId)

    fun getCommentsForTrick(trickId: Long): Flow<List<CommentEntity>> = commentDao.getCommentsForTrick(trickId)

    suspend fun seedDatabaseIfEmpty() {
        if (trickDao.getCount() == 0) {
            trickDao.insertAll(SeedData.initialTricks)
        }
        if (gameProgressDao.getCount() == 0) {
            gameProgressDao.insertAll(SeedData.initialGames)
        }
        if (commentDao.getCount() == 0) {
            commentDao.insertAll(SeedData.initialComments)
        }
    }

    suspend fun setActiveGame(gameId: String) {
        gameProgressDao.setActiveGame(gameId)
    }

    suspend fun updateCurrentStage(gameId: String, stage: String) {
        gameProgressDao.updateCurrentStage(gameId, stage)
    }

    suspend fun toggleUpvote(trick: GameTrickEntity) {
        val newUpvoted = !trick.isUpvoted
        val newVotes = if (newUpvoted) trick.upvotes + 1 else (trick.upvotes - 1).coerceAtLeast(0)
        trickDao.updateUpvote(trick.id, newUpvoted, newVotes)
    }

    suspend fun toggleBookmark(trick: GameTrickEntity) {
        trickDao.updateSaved(trick.id, !trick.isSaved)
    }

    suspend fun toggleLevelCleared(trick: GameTrickEntity) {
        val newCleared = !trick.isLevelCleared
        trickDao.updateLevelCleared(trick.id, newCleared)
        updateGameClearedStats(trick.gameId)
    }

    private suspend fun updateGameClearedStats(gameId: String) {
        val tricks = trickDao.getTricksByGame(gameId).firstOrNull() ?: emptyList()
        val cleared = tricks.count { it.isLevelCleared }
        gameProgressDao.updateClearedCount(gameId, cleared)
    }

    suspend fun addTrick(trick: GameTrickEntity): Long {
        return trickDao.insertTrick(trick)
    }

    suspend fun addComment(trickId: Long, authorName: String, authorRank: String, content: String): Long {
        val comment = CommentEntity(
            trickId = trickId,
            authorName = authorName,
            authorRank = authorRank,
            content = content,
            likes = 0,
            isLiked = false
        )
        val id = commentDao.insertComment(comment)
        trickDao.incrementCommentsCount(trickId)
        return id
    }

    suspend fun toggleCommentLike(comment: CommentEntity) {
        val newLiked = !comment.isLiked
        val newLikes = if (newLiked) comment.likes + 1 else (comment.likes - 1).coerceAtLeast(0)
        commentDao.updateLike(comment.id, newLikes, newLiked)
    }

    suspend fun toggleGameFavorite(gameId: String, currentFavorite: Boolean) {
        gameProgressDao.updateFavorite(gameId, !currentFavorite)
    }
}
