package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameTrickEntity
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyPinkLight
import com.example.ui.theme.DarkCleanBorder
import com.example.ui.theme.DarkCleanSurface
import com.example.ui.theme.DarkCleanSurfaceVariant
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.LimePop
import com.example.ui.theme.PixelBorderBlue
import com.example.ui.theme.PixelClearGreen
import com.example.ui.theme.PixelGoldStar
import com.example.ui.theme.PixelSkyCyan
import com.example.ui.theme.PressStartFontFamily
import com.example.ui.theme.SilkscreenFontFamily
import com.example.ui.theme.SunshineYellow
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextPureWhite
import com.example.ui.theme.TextSoftWhite

@Composable
fun GuideCard(
    trick: GameTrickEntity,
    onClick: () -> Unit,
    onUpvoteClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onClearToggleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val upvoteBgColor by animateColorAsState(
        targetValue = if (trick.isUpvoted) CandyPink.copy(alpha = 0.2f) else DarkCleanSurfaceVariant,
        label = "upvoteBg"
    )
    val upvoteIconColor by animateColorAsState(
        targetValue = if (trick.isUpvoted) CandyPinkLight else TextMutedGray,
        label = "upvoteColor"
    )
    val clearedColor by animateColorAsState(
        targetValue = if (trick.isLevelCleared) LimePop else TextMutedGray,
        label = "clearedColor"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("guide_card_${trick.id}")
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkCleanSurface
        ),
        border = BorderStroke(
            width = if (trick.isLevelCleared) 1.5.dp else 1.dp,
            color = if (trick.isLevelCleared) PixelClearGreen else PixelBorderBlue
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Top Row: Game Name, Category, and Difficulty
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Surface(
                        color = PixelSkyCyan.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.dp, PixelSkyCyan)
                    ) {
                        Text(
                            text = trick.gameTitle.split(":").first(),
                            color = PixelSkyCyan,
                            fontSize = 10.sp,
                            fontFamily = SilkscreenFontFamily,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    CategoryBadge(category = trick.category)
                }

                DifficultyBadge(difficulty = trick.difficulty)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Level / Stage breadcrumb
            Text(
                text = "▶ ${trick.levelName}",
                color = PixelGoldStar,
                fontSize = 11.sp,
                fontFamily = SilkscreenFontFamily,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Guide Title
            Text(
                text = trick.title,
                color = TextPureWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 22.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Summary
            Text(
                text = trick.summary,
                color = TextSoftWhite,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Steps count & Estimated clear time chips
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = DarkCleanSurfaceVariant,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "${trick.steps.size} steps",
                        color = TextSoftWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Estimated time",
                        tint = TextMutedGray,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${trick.estimatedMinutes} min clear",
                        color = TextMutedGray,
                        fontSize = 11.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Comments",
                        tint = TextMutedGray,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${trick.commentsCount}",
                        color = TextMutedGray,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Author
                Text(
                    text = "by ${trick.authorName}",
                    color = TextMutedGray,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom Actions Bar: Upvote, Level Cleared Toggle, Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Upvote button
                Surface(
                    modifier = Modifier
                        .testTag("upvote_btn_${trick.id}")
                        .clickable { onUpvoteClick() },
                    shape = RoundedCornerShape(20.dp),
                    color = upvoteBgColor,
                    border = BorderStroke(
                        0.5.dp,
                        if (trick.isUpvoted) CandyPink.copy(alpha = 0.5f) else DarkCleanBorder
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (trick.isUpvoted) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                            contentDescription = "Upvote guide",
                            tint = upvoteIconColor,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${trick.upvotes}",
                            color = if (trick.isUpvoted) CandyPinkLight else TextPureWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Level Cleared Toggle
                    Surface(
                        modifier = Modifier
                            .testTag("clear_toggle_${trick.id}")
                            .clickable { onClearToggleClick() },
                        shape = RoundedCornerShape(20.dp),
                        color = if (trick.isLevelCleared) LimePop.copy(alpha = 0.15f) else DarkCleanSurfaceVariant,
                        border = BorderStroke(
                            0.5.dp,
                            if (trick.isLevelCleared) LimePop.copy(alpha = 0.5f) else DarkCleanBorder
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (trick.isLevelCleared) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                contentDescription = "Level cleared toggle",
                                tint = clearedColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = if (trick.isLevelCleared) "CLEARED! ✓" else "MARK CLEAR",
                                color = if (trick.isLevelCleared) PixelClearGreen else TextSoftWhite,
                                fontSize = 10.sp,
                                fontFamily = SilkscreenFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Bookmark IconButton
                    IconButton(
                        onClick = onBookmarkClick,
                        modifier = Modifier
                            .testTag("bookmark_btn_${trick.id}")
                            .size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (trick.isSaved) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                            contentDescription = if (trick.isSaved) "Remove from saved" else "Save guide",
                            tint = if (trick.isSaved) SunshineYellow else TextMutedGray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
