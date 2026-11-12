package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.GameTacticsDatabase
import com.example.data.model.CommentEntity
import com.example.data.model.GameProgressEntity
import com.example.data.model.GameTrickEntity
import com.example.data.repository.TrickRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class SortOption(val displayName: String) {
    MOST_UPVOTED("🔥 Top Tips"),
    NEWEST("⚡ Fresh"),
    QUICKEST("⏱️ Fast Clear")
}

data class UserProfile(
    val gamerTag: String = "BeanKnight_99",
    val title: String = "Crown Champion",
    val level: Int = 14,
    val xp: Int = 3200,
    val nextLevelXp: Int = 4000
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = GameTacticsDatabase.getDatabase(application)
    private val repository = TrickRepository(
        database.trickDao(),
        database.commentDao(),
        database.gameProgressDao()
    )

    // User profile state
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    // Filters and Search
    val searchQuery = MutableStateFlow("")
    val selectedGameFilter = MutableStateFlow("All")
    val selectedCategoryFilter = MutableStateFlow("All")
    val selectedSortOption = MutableStateFlow(SortOption.MOST_UPVOTED)

    // Active selected trick for details screen
    private val _selectedTrickId = MutableStateFlow<Long?>(null)
    val selectedTrickId: StateFlow<Long?> = _selectedTrickId.asStateFlow()

    // Database Flows
    val allTricks: StateFlow<List<GameTrickEntity>> = repository.allTricks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedTricks: StateFlow<List<GameTrickEntity>> = repository.savedTricks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allGamesProgress: StateFlow<List<GameProgressEntity>> = repository.allGamesProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Currently playing active game
    val currentActiveGame: StateFlow<GameProgressEntity?> = repository.currentActiveGame
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Recommended tricks specifically for the currently playing active game
    val recommendedTricksForActiveGame: StateFlow<List<GameTrickEntity>> = combine(
        allTricks,
        currentActiveGame
    ) { tricks, activeGame ->
        if (activeGame == null) {
            tricks.take(4)
        } else {
            tricks.filter { it.gameId.equals(activeGame.gameId, ignoreCase = true) }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered and sorted tricks stream
    val filteredTricks: StateFlow<List<GameTrickEntity>> = combine(
        allTricks,
        searchQuery,
        selectedGameFilter,
        selectedCategoryFilter,
        selectedSortOption
    ) { tricks, query, game, category, sort ->
        var list = tricks

        // Filter by game
        if (game != "All") {
            list = list.filter { it.gameTitle.equals(game, ignoreCase = true) || it.gameId.equals(game, ignoreCase = true) }
        }

        // Filter by category
        if (category != "All") {
            list = list.filter { it.category.equals(category, ignoreCase = true) }
        }

        // Search query
        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter { trick ->
                trick.title.lowercase().contains(q) ||
                trick.gameTitle.lowercase().contains(q) ||
                trick.levelName.lowercase().contains(q) ||
                trick.summary.lowercase().contains(q) ||
                trick.proTips.any { it.lowercase().contains(q) } ||
                trick.authorName.lowercase().contains(q)
            }
        }

        // Sort
        when (sort) {
            SortOption.MOST_UPVOTED -> list.sortedByDescending { it.upvotes }
            SortOption.NEWEST -> list.sortedByDescending { it.timestamp }
            SortOption.QUICKEST -> list.sortedBy { it.estimatedMinutes }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selected trick flow
    val selectedTrick: StateFlow<GameTrickEntity?> = _selectedTrickId.flatMapLatest { id ->
        if (id == null) flowOf(null) else repository.getTrickById(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Comments for the selected trick
    val commentsForSelectedTrick: StateFlow<List<CommentEntity>> = _selectedTrickId.flatMapLatest { id ->
        if (id == null) flowOf(emptyList()) else repository.getCommentsForTrick(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.seedDatabaseIfEmpty()
        }
    }

    fun selectActiveGame(gameId: String) {
        viewModelScope.launch {
            repository.setActiveGame(gameId)
        }
    }

    fun updateActiveGameStage(gameId: String, newStage: String) {
        viewModelScope.launch {
            repository.updateCurrentStage(gameId, newStage)
        }
    }

    fun selectTrick(id: Long?) {
        _selectedTrickId.value = id
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun selectGameFilter(game: String) {
        selectedGameFilter.value = game
    }

    fun selectCategoryFilter(category: String) {
        selectedCategoryFilter.value = category
    }

    fun selectSortOption(sort: SortOption) {
        selectedSortOption.value = sort
    }

    fun toggleUpvote(trick: GameTrickEntity) {
        viewModelScope.launch {
            repository.toggleUpvote(trick)
        }
    }

    fun toggleBookmark(trick: GameTrickEntity) {
        viewModelScope.launch {
            repository.toggleBookmark(trick)
        }
    }

    fun toggleLevelCleared(trick: GameTrickEntity) {
        viewModelScope.launch {
            repository.toggleLevelCleared(trick)
            val cur = _userProfile.value
            _userProfile.value = cur.copy(xp = cur.xp + 50)
        }
    }

    fun addComment(trickId: Long, content: String) {
        if (content.isBlank()) return
        viewModelScope.launch {
            val profile = _userProfile.value
            repository.addComment(
                trickId = trickId,
                authorName = profile.gamerTag,
                authorRank = profile.title,
                content = content.trim()
            )
            _userProfile.value = profile.copy(xp = profile.xp + 25)
        }
    }

    fun toggleCommentLike(comment: CommentEntity) {
        viewModelScope.launch {
            repository.toggleCommentLike(comment)
        }
    }

    fun createGuide(
        gameTitle: String,
        levelName: String,
        category: String,
        difficulty: String,
        title: String,
        summary: String,
        steps: List<String>,
        proTips: List<String>,
        estimatedMinutes: Int
    ) {
        viewModelScope.launch {
            val profile = _userProfile.value
            val gameId = gameTitle.lowercase().replace(" ", "_").filter { it.isLetterOrDigit() || it == '_' }
            val newTrick = GameTrickEntity(
                gameId = gameId,
                gameTitle = gameTitle.trim(),
                levelName = levelName.trim(),
                category = category,
                difficulty = difficulty,
                title = title.trim(),
                summary = summary.trim(),
                steps = steps.filter { it.isNotBlank() },
                proTips = proTips.filter { it.isNotBlank() },
                authorName = profile.gamerTag,
                authorRank = profile.title,
                upvotes = 1,
                isUpvoted = true,
                isSaved = true,
                isLevelCleared = false,
                estimatedMinutes = estimatedMinutes,
                commentsCount = 0
            )
            val id = repository.addTrick(newTrick)
            _selectedTrickId.value = id
            _userProfile.value = profile.copy(xp = profile.xp + 150)
        }
    }

    fun updateGamerTag(newTag: String) {
        if (newTag.isNotBlank()) {
            _userProfile.value = _userProfile.value.copy(gamerTag = newTag.trim())
        }
    }
}
