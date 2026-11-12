package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.GameTrickEntity
import com.example.ui.SortOption
import com.example.ui.components.GuideCard
import com.example.ui.theme.CandyPink
import com.example.ui.theme.DarkCleanBackground
import com.example.ui.theme.DarkCleanBorder
import com.example.ui.theme.DarkCleanSurface
import com.example.ui.theme.DarkCleanSurfaceVariant
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.LimePop
import com.example.ui.theme.PixelBackgroundDark
import com.example.ui.theme.PixelBluePrimary
import com.example.ui.theme.PixelSkyCyan
import com.example.ui.theme.PressStartFontFamily
import com.example.ui.theme.SunshineYellow
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextPureWhite
import com.example.ui.theme.TextSoftWhite

@Composable
fun ExploreFeedScreen(
    tricks: List<GameTrickEntity>,
    totalCleared: Int,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    selectedGame: String,
    onSelectGame: (String) -> Unit,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
    selectedSort: SortOption,
    onSelectSort: (SortOption) -> Unit,
    onTrickClick: (GameTrickEntity) -> Unit,
    onUpvoteClick: (GameTrickEntity) -> Unit,
    onBookmarkClick: (GameTrickEntity) -> Unit,
    onClearToggleClick: (GameTrickEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val games = listOf("All", "Fall Guys", "Zelda: TotK", "Elden Ring", "Hollow Knight", "Cyberpunk 2077")
    val categories = listOf("All", "Level Walkthrough", "Boss Guide", "Shortcut", "Speedrun", "Puzzle")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("explore_feed_list"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pixel_game_hero_1794495239820),
                    contentDescription = "8-Bit Pixel Game Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    PixelBackgroundDark.copy(alpha = 0.85f),
                                    PixelBackgroundDark
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Surface(
                        color = PixelBluePrimary,
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.dp, PixelSkyCyan)
                    ) {
                        Text(
                            text = "▼ LEVEL CLEAR MANUAL",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontFamily = PressStartFontFamily,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "8-BIT TACTICS HUB",
                        color = TextPureWhite,
                        fontSize = 16.sp,
                        fontFamily = PressStartFontFamily
                    )
                }
            }
        }

        // Search Bar
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("feed_search_input"),
                    placeholder = {
                        Text(
                            "Search tricks, obstacles, shortcuts...",
                            color = TextMutedGray,
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = ElectricCyan
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotBlank()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search",
                                    tint = TextMutedGray
                                )
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CandyPink,
                        unfocusedBorderColor = DarkCleanBorder,
                        focusedContainerColor = DarkCleanSurface,
                        unfocusedContainerColor = DarkCleanSurface,
                        focusedTextColor = TextPureWhite,
                        unfocusedTextColor = TextPureWhite
                    ),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true
                )
            }
        }

        // Game selector horizontal row
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(games) { game ->
                    val isSelected = selectedGame.equals(game, ignoreCase = true)
                    Surface(
                        modifier = Modifier
                            .testTag("game_chip_${game.lowercase().replace(" ", "_")}")
                            .clickable { onSelectGame(game) },
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) CandyPink else DarkCleanSurfaceVariant,
                        border = BorderStroke(
                            0.5.dp,
                            if (isSelected) Color.White else DarkCleanBorder
                        )
                    ) {
                        Text(
                            text = game,
                            color = if (isSelected) Color.White else TextSoftWhite,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Category filter chips
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory.equals(cat, ignoreCase = true)
                    Surface(
                        modifier = Modifier
                            .testTag("cat_chip_${cat.lowercase().replace(" ", "_")}")
                            .clickable { onSelectCategory(cat) },
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) ElectricCyan.copy(alpha = 0.2f) else DarkCleanSurface,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) ElectricCyan else DarkCleanBorder
                        )
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) ElectricCyan else TextMutedGray,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }

        // Sort Options Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${tricks.size} Guides",
                    color = TextSoftWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    SortOption.values().forEach { sort ->
                        val isSelected = selectedSort == sort
                        Surface(
                            modifier = Modifier.clickable { onSelectSort(sort) },
                            shape = RoundedCornerShape(6.dp),
                            color = if (isSelected) DarkCleanSurfaceVariant else Color.Transparent,
                            border = if (isSelected) BorderStroke(0.5.dp, SunshineYellow) else null
                        ) {
                            Text(
                                text = sort.displayName,
                                color = if (isSelected) SunshineYellow else TextMutedGray,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }
        }

        // Guides Feed
        if (tricks.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkCleanSurface),
                    border = BorderStroke(0.5.dp, DarkCleanBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No tricks found",
                            color = TextPureWhite,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Try clearing your search query or publish a new guide for this level!",
                            color = TextMutedGray,
                            fontSize = 13.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(tricks, key = { it.id }) { trick ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    GuideCard(
                        trick = trick,
                        onClick = { onTrickClick(trick) },
                        onUpvoteClick = { onUpvoteClick(trick) },
                        onBookmarkClick = { onBookmarkClick(trick) },
                        onClearToggleClick = { onClearToggleClick(trick) }
                    )
                }
            }
        }
    }
}
