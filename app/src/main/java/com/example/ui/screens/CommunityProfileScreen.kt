package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.UserProfile
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyPinkLight
import com.example.ui.theme.DarkCleanBackground
import com.example.ui.theme.DarkCleanBorder
import com.example.ui.theme.DarkCleanSurface
import com.example.ui.theme.DarkCleanSurfaceVariant
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.LimePop
import com.example.ui.theme.SunshineYellow
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextPureWhite
import com.example.ui.theme.TextSoftWhite

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CommunityProfileScreen(
    userProfile: UserProfile,
    totalGuidesCreated: Int,
    totalLevelsCleared: Int,
    onUpdateGamerTag: (String) -> Unit,
    onOpenAddGuide: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var editTagText by remember { mutableStateOf(userProfile.gamerTag) }

    val xpProgress = (userProfile.xp.toFloat() / userProfile.nextLevelXp.toFloat()).coerceIn(0f, 1f)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("community_profile_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            // Profile Card Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCleanSurface),
                border = BorderStroke(1.5.dp, CandyPink)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(CandyPink, CircleShape)
                            .border(2.dp, CandyPinkLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userProfile.gamerTag.take(1).uppercase(),
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = userProfile.gamerTag,
                            color = TextPureWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = {
                                editTagText = userProfile.gamerTag
                                showEditDialog = true
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit GamerTag",
                                tint = ElectricCyan,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Text(
                        text = userProfile.title,
                        color = SunshineYellow,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // XP bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Level ${userProfile.level}",
                            color = SunshineYellow,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${userProfile.xp} / ${userProfile.nextLevelXp} XP",
                            color = TextSoftWhite,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { xpProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = SunshineYellow,
                        trackColor = DarkCleanSurfaceVariant
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onOpenAddGuide,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("profile_share_guide_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CandyPink,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share New Trick (+150 XP)", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Stats Matrix
        item {
            Text(
                text = "COMMUNITY REPUTATION",
                color = TextPureWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ReputationCard(
                    title = "Guides Shared",
                    value = "$totalGuidesCreated",
                    icon = Icons.Default.EmojiEvents,
                    color = CandyPinkLight,
                    modifier = Modifier.weight(1f)
                )
                ReputationCard(
                    title = "Levels Cleared",
                    value = "$totalLevelsCleared",
                    icon = Icons.Default.CheckCircle,
                    color = LimePop,
                    modifier = Modifier.weight(1f)
                )
                ReputationCard(
                    title = "Rank",
                    value = "Top 5%",
                    icon = Icons.Default.Shield,
                    color = ElectricCyan,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Badges Section
        item {
            Text(
                text = "TACTICIAN TROPHIES & BADGES",
                color = SunshineYellow,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BadgeChip(title = "👑 Fall Guys Crown", unlocked = true)
                BadgeChip(title = "⚡ Speedrun Analyst", unlocked = true)
                BadgeChip(title = "🏆 Obstacle Conqueror", unlocked = totalLevelsCleared >= 2)
                BadgeChip(title = "🛡️ Community Guide Author", unlocked = totalGuidesCreated > 0)
                BadgeChip(title = "⭐ Master Tactician 500+", unlocked = userProfile.xp >= 2000)
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = {
                Text("Change GamerTag", color = TextPureWhite, fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(
                        "Enter the GamerTag you want fellow players to see:",
                        color = TextSoftWhite,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = editTagText,
                        onValueChange = { editTagText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("edit_gamertag_input"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CandyPink,
                            unfocusedBorderColor = DarkCleanBorder,
                            focusedContainerColor = DarkCleanSurfaceVariant,
                            unfocusedContainerColor = DarkCleanSurfaceVariant,
                            focusedTextColor = TextPureWhite,
                            unfocusedTextColor = TextPureWhite
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editTagText.isNotBlank()) {
                            onUpdateGamerTag(editTagText)
                            showEditDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CandyPink)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showEditDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkCleanSurfaceVariant)
                ) {
                    Text("Cancel", color = TextSoftWhite)
                }
            },
            containerColor = DarkCleanSurface
        )
    }
}

@Composable
private fun ReputationCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCleanSurface),
        border = BorderStroke(0.5.dp, DarkCleanBorder)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                color = TextPureWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                color = TextMutedGray,
                fontSize = 10.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
private fun BadgeChip(
    title: String,
    unlocked: Boolean
) {
    Surface(
        color = if (unlocked) DarkCleanSurfaceVariant else DarkCleanBackground,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            0.5.dp,
            if (unlocked) SunshineYellow.copy(alpha = 0.5f) else DarkCleanBorder
        )
    ) {
        Text(
            text = title,
            color = if (unlocked) SunshineYellow else TextMutedGray,
            fontSize = 12.sp,
            fontWeight = if (unlocked) FontWeight.SemiBold else FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}
