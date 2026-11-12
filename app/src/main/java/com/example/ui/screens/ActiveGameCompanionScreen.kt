package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.GameProgressEntity
import com.example.data.model.GameTrickEntity
import com.example.ui.components.GuideCard
import com.example.ui.theme.PixelBackgroundDark
import com.example.ui.theme.PixelBlueLight
import com.example.ui.theme.PixelBluePrimary
import com.example.ui.theme.PixelBorderBlue
import com.example.ui.theme.PixelBorderSubtle
import com.example.ui.theme.PixelClearGreen
import com.example.ui.theme.PixelGoldStar
import com.example.ui.theme.PixelHeartRed
import com.example.ui.theme.PixelSkyCyan
import com.example.ui.theme.PixelSurfaceCard
import com.example.ui.theme.PixelSurfaceElevated
import com.example.ui.theme.PressStartFontFamily
import com.example.ui.theme.SilkscreenFontFamily
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextPureWhite
import com.example.ui.theme.TextSoftWhite

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ActiveGameCompanionScreen(
    activeGame: GameProgressEntity?,
    allGames: List<GameProgressEntity>,
    recommendedTricks: List<GameTrickEntity>,
    onSelectActiveGame: (String) -> Unit,
    onUpdateStage: (String, String) -> Unit,
    onTrickClick: (GameTrickEntity) -> Unit,
    onUpvoteClick: (GameTrickEntity) -> Unit,
    onBookmarkClick: (GameTrickEntity) -> Unit,
    onClearToggleClick: (GameTrickEntity) -> Unit,
    onOpenAddGuide: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showEditStageDialog by remember { mutableStateOf(false) }
    var newStageInput by remember { mutableStateOf("") }

    val game = activeGame ?: allGames.firstOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("active_game_companion_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // TOP RETRO ARCADE STATUS BAR (Score, Lives, Star Coins)
        item {
            Surface(
                color = PixelSurfaceElevated,
                border = BorderStroke(1.dp, PixelBorderBlue),
                shape = RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "❤️ x3",
                            fontSize = 12.sp,
                            fontFamily = PressStartFontFamily,
                            color = PixelHeartRed
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "⭐ 048750",
                            fontSize = 10.sp,
                            fontFamily = PressStartFontFamily,
                            color = PixelGoldStar
                        )
                    }

                    Surface(
                        color = PixelBluePrimary,
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, PixelSkyCyan)
                    ) {
                        Text(
                            text = "PLAYER 1",
                            color = TextPureWhite,
                            fontSize = 9.sp,
                            fontFamily = PressStartFontFamily,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // QUICK GAME SWITCHER ROW (Horizontal 8-bit Chips)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "SELECT ACTIVE GAME",
                    fontFamily = SilkscreenFontFamily,
                    color = PixelSkyCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allGames) { g ->
                    val isSelected = game?.gameId == g.gameId
                    Surface(
                        modifier = Modifier
                            .testTag("select_game_${g.gameId}")
                            .clickable { onSelectActiveGame(g.gameId) },
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) PixelSkyCyan else PixelSurfaceElevated,
                        border = BorderStroke(
                            if (isSelected) 2.dp else 1.dp,
                            if (isSelected) TextPureWhite else PixelBorderSubtle
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isSelected) "▶ " else "  ",
                                fontSize = 10.sp,
                                fontFamily = PressStartFontFamily,
                                color = if (isSelected) Color(0xFF002845) else PixelSkyCyan
                            )
                            Text(
                                text = g.gameTitle.split(":").first(),
                                color = if (isSelected) Color(0xFF002845) else TextSoftWhite,
                                fontSize = 11.sp,
                                fontFamily = SilkscreenFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        if (game != null) {
            // HERO SPOTLIGHT: Blue Illustrative Pixel Game Window
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .testTag("now_playing_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PixelSurfaceCard),
                    border = BorderStroke(2.dp, PixelBorderBlue)
                ) {
                    Column {
                        // Retro Window Title Bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(PixelBluePrimary)
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "▼ ACTIVE_STAGE_INFO.EXE",
                                    color = TextPureWhite,
                                    fontSize = 10.sp,
                                    fontFamily = PressStartFontFamily
                                )
                                Text(
                                    text = "[●][■][X]",
                                    color = PixelSkyCyan,
                                    fontSize = 10.sp,
                                    fontFamily = SilkscreenFontFamily
                                )
                            }
                        }

                        // Hero Window Body
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = game.gameTitle,
                                    color = TextPureWhite,
                                    fontSize = 18.sp,
                                    fontFamily = SilkscreenFontFamily,
                                    fontWeight = FontWeight.Bold
                                )

                                Surface(
                                    color = PixelGoldStar.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(4.dp),
                                    border = BorderStroke(1.dp, PixelGoldStar)
                                ) {
                                    Text(
                                        text = game.genre,
                                        color = PixelGoldStar,
                                        fontSize = 10.sp,
                                        fontFamily = SilkscreenFontFamily,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = game.tagline,
                                color = PixelSkyCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Current Stage Box (8-bit Dialog Style)
                            Surface(
                                color = PixelSurfaceElevated,
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, PixelSkyCyan),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "CURRENT LEVEL / STAGE:",
                                            color = PixelGoldStar,
                                            fontSize = 9.sp,
                                            fontFamily = PressStartFontFamily
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = game.currentStage,
                                            color = TextPureWhite,
                                            fontSize = 14.sp,
                                            fontFamily = SilkscreenFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Button(
                                        onClick = {
                                            newStageInput = game.currentStage
                                            showEditStageDialog = true
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = PixelSkyCyan,
                                            contentColor = Color(0xFF002845)
                                        ),
                                        shape = RoundedCornerShape(6.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("SET", fontSize = 10.sp, fontFamily = PressStartFontFamily)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Clearance Progress Bar
                            val progress = if (game.totalKnownLevels > 0) {
                                game.clearedCount.toFloat() / game.totalKnownLevels.toFloat()
                            } else 0f

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "STAGE PROGRESS",
                                    color = TextSoftWhite,
                                    fontSize = 10.sp,
                                    fontFamily = SilkscreenFontFamily
                                )
                                Text(
                                    text = "${game.clearedCount}/${game.totalKnownLevels} [ ${(progress * 100).toInt()}% ]",
                                    color = PixelClearGreen,
                                    fontSize = 10.sp,
                                    fontFamily = PressStartFontFamily
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = PixelClearGreen,
                                trackColor = PixelSurfaceElevated
                            )
                        }
                    }
                }
            }

            // WINDOW 2: GAMEPLAY BRIEF & CONTROLS (Pixel style)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PixelSurfaceCard),
                    border = BorderStroke(1.5.dp, PixelBorderBlue)
                ) {
                    Column {
                        // Title bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF1E3A8A))
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "▼ GAME_DATABASE_MANUAL",
                                color = PixelGoldStar,
                                fontSize = 10.sp,
                                fontFamily = PressStartFontFamily
                            )
                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = game.description,
                                color = TextPureWhite,
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "CORE MECHANICS & BUTTON COMBO:",
                                color = PixelSkyCyan,
                                fontSize = 10.sp,
                                fontFamily = SilkscreenFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                game.getKeyMechanicsList().forEach { mech ->
                                    Surface(
                                        color = PixelSurfaceElevated,
                                        shape = RoundedCornerShape(6.dp),
                                        border = BorderStroke(1.dp, PixelBorderSubtle)
                                    ) {
                                        Text(
                                            text = "🎮 $mech",
                                            color = TextPureWhite,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // WINDOW 3: RECOMMENDED CLEARANCE CHEAT-SHEET
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2547)),
                    border = BorderStroke(2.dp, PixelSkyCyan)
                ) {
                    Column {
                        // Title bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(PixelSkyCyan)
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "★ LEVEL CLEAR CHEAT-SHEET",
                                    color = Color(0xFF002845),
                                    fontSize = 10.sp,
                                    fontFamily = PressStartFontFamily
                                )
                            }
                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            game.getRecommendedTipsList().forEachIndexed { idx, tip ->
                                Surface(
                                    color = PixelSurfaceElevated,
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, PixelBorderBlue.copy(alpha = 0.5f)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "[#${idx + 1}]",
                                            color = PixelGoldStar,
                                            fontSize = 10.sp,
                                            fontFamily = PressStartFontFamily
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = tip,
                                            color = TextPureWhite,
                                            fontSize = 13.sp,
                                            lineHeight = 18.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // One-Tap "STAGE CLEARED!" Button
                            val firstTrick = recommendedTricks.firstOrNull()
                            if (firstTrick != null) {
                                Button(
                                    onClick = { onClearToggleClick(firstTrick) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .testTag("beat_level_btn"),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (firstTrick.isLevelCleared) PixelClearGreen else PixelSkyCyan,
                                        contentColor = if (firstTrick.isLevelCleared) Color.White else Color(0xFF002845)
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.5.dp, TextPureWhite)
                                ) {
                                    Text(
                                        text = if (firstTrick.isLevelCleared) "★ STAGE CLEARED! (+50 XP) ★" else "MARK STAGE CLEARED (+50 XP)",
                                        fontSize = 10.sp,
                                        fontFamily = PressStartFontFamily
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // WINDOW 4: COMMUNITY GUIDES LIST
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "COMMUNITY TACTICS",
                        color = PixelSkyCyan,
                        fontSize = 13.sp,
                        fontFamily = SilkscreenFontFamily,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = onOpenAddGuide,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PixelBluePrimary,
                            contentColor = TextPureWhite
                        ),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, PixelSkyCyan),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("+ ADD TRICK", fontSize = 9.sp, fontFamily = PressStartFontFamily)
                    }
                }
            }

            if (recommendedTricks.isEmpty()) {
                item {
                    Text(
                        text = "No tricks posted yet for this game. Tap '+ ADD TRICK' to publish!",
                        color = TextMutedGray,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
            } else {
                items(recommendedTricks, key = { it.id }) { trick ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)) {
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

    // Dialog to update current stage / round
    if (showEditStageDialog && game != null) {
        AlertDialog(
            onDismissRequest = { showEditStageDialog = false },
            title = {
                Text(
                    text = "SET CURRENT STAGE",
                    color = PixelSkyCyan,
                    fontSize = 12.sp,
                    fontFamily = PressStartFontFamily
                )
            },
            text = {
                Column {
                    Text(
                        text = "Enter current level or boss for ${game.gameTitle.split(':').first()}:",
                        color = TextSoftWhite,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = newStageInput,
                        onValueChange = { newStageInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("current_stage_input"),
                        placeholder = { Text("e.g. Stage 3: Volleyfall or Castle Gate") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PixelSkyCyan,
                            unfocusedBorderColor = PixelBorderSubtle,
                            focusedTextColor = TextPureWhite,
                            unfocusedTextColor = TextPureWhite,
                            focusedContainerColor = PixelSurfaceElevated,
                            unfocusedContainerColor = PixelSurfaceElevated
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newStageInput.isNotBlank()) {
                            onUpdateStage(game.gameId, newStageInput.trim())
                            showEditStageDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PixelSkyCyan, contentColor = Color(0xFF002845)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("SAVE", fontSize = 10.sp, fontFamily = PressStartFontFamily)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showEditStageDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = PixelSurfaceElevated),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("CANCEL", color = TextSoftWhite, fontSize = 10.sp, fontFamily = PressStartFontFamily)
                }
            },
            containerColor = PixelSurfaceCard
        )
    }
}
