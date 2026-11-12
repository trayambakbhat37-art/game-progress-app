package com.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.ActiveGameCompanionScreen
import com.example.ui.screens.AddGuideDialog
import com.example.ui.screens.ExploreFeedScreen
import com.example.ui.screens.GuideDetailScreen
import com.example.ui.screens.SavedGuidesScreen
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyPinkLight
import com.example.ui.theme.DarkCleanBackground
import com.example.ui.theme.DarkCleanSurface
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.PixelSkyCyan
import com.example.ui.theme.PixelSurfaceCard
import com.example.ui.theme.SilkscreenFontFamily
import com.example.ui.theme.TextMutedGray
import kotlinx.coroutines.launch

enum class AppTab(val title: String, val icon: ImageVector, val tag: String) {
    COMPANION("Playing Now", Icons.Default.Gamepad, "nav_companion"),
    EXPLORE("All Guides", Icons.Default.Explore, "nav_explore"),
    SAVED("Saved", Icons.Default.Bookmark, "nav_saved")
}

@Composable
fun GameTacticsApp(
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var currentTab by remember { mutableStateOf(AppTab.COMPANION) }
    var showAddGuideDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // State collections from ViewModel
    val activeGame by viewModel.currentActiveGame.collectAsStateWithLifecycle()
    val allGames by viewModel.allGamesProgress.collectAsStateWithLifecycle()
    val recommendedTricks by viewModel.recommendedTricksForActiveGame.collectAsStateWithLifecycle()

    val filteredTricks by viewModel.filteredTricks.collectAsStateWithLifecycle()
    val allTricks by viewModel.allTricks.collectAsStateWithLifecycle()
    val savedTricks by viewModel.savedTricks.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedGame by viewModel.selectedGameFilter.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategoryFilter.collectAsStateWithLifecycle()
    val selectedSort by viewModel.selectedSortOption.collectAsStateWithLifecycle()

    val selectedTrick by viewModel.selectedTrick.collectAsStateWithLifecycle()
    val commentsForSelectedTrick by viewModel.commentsForSelectedTrick.collectAsStateWithLifecycle()

    val totalCleared = allTricks.count { it.isLevelCleared }

    // If a trick is selected, show GuideDetailScreen
    if (selectedTrick != null) {
        val currentTrick = selectedTrick!!
        GuideDetailScreen(
            trick = currentTrick,
            comments = commentsForSelectedTrick,
            onBack = { viewModel.selectTrick(null) },
            onUpvoteClick = { viewModel.toggleUpvote(currentTrick) },
            onBookmarkClick = {
                viewModel.toggleBookmark(currentTrick)
                coroutineScope.launch {
                    val msg = if (!currentTrick.isSaved) "Guide saved to playbook!" else "Removed from saved"
                    snackbarHostState.showSnackbar(msg)
                }
            },
            onClearToggleClick = {
                viewModel.toggleLevelCleared(currentTrick)
                coroutineScope.launch {
                    val msg = if (!currentTrick.isLevelCleared) "Level Marked Cleared! +50 XP" else "Cleared status reset"
                    snackbarHostState.showSnackbar(msg)
                }
            },
            onAddComment = { content ->
                viewModel.addComment(currentTrick.id, content)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Comment posted! +25 XP")
                }
            },
            onCommentLikeClick = { comment ->
                viewModel.toggleCommentLike(comment)
            }
        )
    } else {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = DarkCleanBackground,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                NavigationBar(
                    containerColor = PixelSurfaceCard,
                    tonalElevation = 8.dp
                ) {
                    AppTab.values().forEach { tab ->
                        val isSelected = currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentTab = tab },
                            modifier = Modifier.testTag(tab.tag),
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = tab.title,
                                    fontSize = 10.sp,
                                    fontFamily = SilkscreenFontFamily,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PixelSkyCyan,
                                selectedTextColor = PixelSkyCyan,
                                unselectedIconColor = TextMutedGray,
                                unselectedTextColor = TextMutedGray,
                                indicatorColor = PixelSkyCyan.copy(alpha = 0.2f)
                            )
                        )
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddGuideDialog = true },
                    modifier = Modifier.testTag("fab_add_guide"),
                    containerColor = PixelSkyCyan,
                    contentColor = Color(0xFF002845),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Share Guide",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (currentTab) {
                    AppTab.COMPANION -> {
                        ActiveGameCompanionScreen(
                            activeGame = activeGame,
                            allGames = allGames,
                            recommendedTricks = recommendedTricks,
                            onSelectActiveGame = { gameId ->
                                viewModel.selectActiveGame(gameId)
                            },
                            onUpdateStage = { gameId, stage ->
                                viewModel.updateActiveGameStage(gameId, stage)
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Current stage updated to $stage!")
                                }
                            },
                            onTrickClick = { viewModel.selectTrick(it.id) },
                            onUpvoteClick = { viewModel.toggleUpvote(it) },
                            onBookmarkClick = {
                                viewModel.toggleBookmark(it)
                                coroutineScope.launch {
                                    val msg = if (!it.isSaved) "Guide saved to playbook!" else "Removed from saved"
                                    snackbarHostState.showSnackbar(msg)
                                }
                            },
                            onClearToggleClick = {
                                viewModel.toggleLevelCleared(it)
                                coroutineScope.launch {
                                    val msg = if (!it.isLevelCleared) "Level Marked Cleared! +50 XP" else "Cleared status reset"
                                    snackbarHostState.showSnackbar(msg)
                                }
                            },
                            onOpenAddGuide = { showAddGuideDialog = true }
                        )
                    }
                    AppTab.EXPLORE -> {
                        ExploreFeedScreen(
                            tricks = filteredTricks,
                            totalCleared = totalCleared,
                            searchQuery = searchQuery,
                            onSearchChange = { viewModel.updateSearchQuery(it) },
                            selectedGame = selectedGame,
                            onSelectGame = { viewModel.selectGameFilter(it) },
                            selectedCategory = selectedCategory,
                            onSelectCategory = { viewModel.selectCategoryFilter(it) },
                            selectedSort = selectedSort,
                            onSelectSort = { viewModel.selectSortOption(it) },
                            onTrickClick = { viewModel.selectTrick(it.id) },
                            onUpvoteClick = { viewModel.toggleUpvote(it) },
                            onBookmarkClick = {
                                viewModel.toggleBookmark(it)
                                coroutineScope.launch {
                                    val msg = if (!it.isSaved) "Guide saved to playbook!" else "Removed from saved"
                                    snackbarHostState.showSnackbar(msg)
                                }
                            },
                            onClearToggleClick = {
                                viewModel.toggleLevelCleared(it)
                                coroutineScope.launch {
                                    val msg = if (!it.isLevelCleared) "Level Marked Cleared! +50 XP" else "Cleared status reset"
                                    snackbarHostState.showSnackbar(msg)
                                }
                            }
                        )
                    }
                    AppTab.SAVED -> {
                        SavedGuidesScreen(
                            savedTricks = savedTricks,
                            onTrickClick = { viewModel.selectTrick(it.id) },
                            onUpvoteClick = { viewModel.toggleUpvote(it) },
                            onBookmarkClick = { viewModel.toggleBookmark(it) },
                            onClearToggleClick = { viewModel.toggleLevelCleared(it) }
                        )
                    }
                }
            }
        }
    }

    if (showAddGuideDialog) {
        AddGuideDialog(
            onDismiss = { showAddGuideDialog = false },
            onSubmit = { gameTitle, levelName, category, difficulty, title, summary, steps, proTips, estMinutes ->
                viewModel.createGuide(
                    gameTitle = gameTitle,
                    levelName = levelName,
                    category = category,
                    difficulty = difficulty,
                    title = title,
                    summary = summary,
                    steps = steps,
                    proTips = proTips,
                    estimatedMinutes = estMinutes
                )
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Guide published! +150 XP")
                }
            }
        )
    }
}
