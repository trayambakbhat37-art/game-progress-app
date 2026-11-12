package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CommentEntity
import com.example.data.model.GameTrickEntity
import com.example.ui.components.CategoryBadge
import com.example.ui.components.DifficultyBadge
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideDetailScreen(
    trick: GameTrickEntity,
    comments: List<CommentEntity>,
    onBack: () -> Unit,
    onUpvoteClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onClearToggleClick: () -> Unit,
    onAddComment: (String) -> Unit,
    onCommentLikeClick: (CommentEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var newCommentText by remember { mutableStateOf("") }

    val upvoteBgColor by animateColorAsState(
        targetValue = if (trick.isUpvoted) CandyPink.copy(alpha = 0.25f) else DarkCleanSurfaceVariant,
        label = "upvoteBg"
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkCleanBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = trick.gameTitle.split(":").first(),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPureWhite
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPureWhite
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onBookmarkClick,
                        modifier = Modifier.testTag("detail_bookmark_btn")
                    ) {
                        Icon(
                            imageVector = if (trick.isSaved) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                            contentDescription = "Bookmark guide",
                            tint = if (trick.isSaved) SunshineYellow else TextPureWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkCleanSurface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                // Metadata Badges
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CategoryBadge(category = trick.category)
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = "Estimated Time",
                                tint = TextMutedGray,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "${trick.estimatedMinutes} min clear",
                                color = TextMutedGray,
                                fontSize = 12.sp
                            )
                        }
                    }
                    DifficultyBadge(difficulty = trick.difficulty)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Level / Stage Name
                Text(
                    text = "🎯 ${trick.levelName}",
                    color = ElectricCyan,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Main Guide Title
                Text(
                    text = trick.title,
                    color = TextPureWhite,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Action Buttons: Upvote & Level Cleared
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onUpvoteClick,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("detail_upvote_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (trick.isUpvoted) CandyPink else DarkCleanSurfaceVariant,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = if (trick.isUpvoted) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                            contentDescription = "Upvote",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (trick.isUpvoted) "Upvoted (${trick.upvotes})" else "Upvote (${trick.upvotes})",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = onClearToggleClick,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("detail_clear_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (trick.isLevelCleared) LimePop else DarkCleanSurfaceVariant,
                            contentColor = if (trick.isLevelCleared) Color(0xFF003314) else TextSoftWhite
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = if (trick.isLevelCleared) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                            contentDescription = "Level cleared toggle",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (trick.isLevelCleared) "Cleared! ✓" else "Mark Cleared",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkCleanSurface),
                    border = BorderStroke(0.5.dp, DarkCleanBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "STRATEGY OVERVIEW",
                            color = ElectricCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = trick.summary,
                            color = TextPureWhite,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // STEP BY STEP CLEARANCE SECTION
                Text(
                    text = "STEP-BY-STEP LEVEL CLEARANCE",
                    color = TextPureWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Steps list
            items(trick.steps.indices.toList()) { index ->
                val stepText = trick.steps[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkCleanSurface),
                    border = BorderStroke(0.5.dp, DarkCleanBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(CandyPink.copy(alpha = 0.2f), CircleShape)
                                .border(1.dp, CandyPinkLight, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = CandyPinkLight,
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stepText,
                            color = TextPureWhite,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Pro Tips Section
            if (trick.proTips.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "TACTICAL PRO TIPS & EXPLOITS",
                        color = SunshineYellow,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(trick.proTips) { tip ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF261D12)),
                        border = BorderStroke(0.5.dp, SunshineYellow.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Pro Tip",
                                tint = SunshineYellow,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = tip,
                                color = Color(0xFFFEF3C7),
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }
            }

            // Comments Section Header
            item {
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = DarkCleanBorder)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "COMMUNITY DISCUSSION (${comments.size})",
                        color = TextPureWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Share trick variations",
                        color = TextMutedGray,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Add Comment Box
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newCommentText,
                        onValueChange = { newCommentText = it },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("comment_input"),
                        placeholder = {
                            Text(
                                "Add your trick or question...",
                                color = TextMutedGray,
                                fontSize = 13.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CandyPink,
                            unfocusedBorderColor = DarkCleanBorder,
                            focusedContainerColor = DarkCleanSurface,
                            unfocusedContainerColor = DarkCleanSurface,
                            focusedTextColor = TextPureWhite,
                            unfocusedTextColor = TextPureWhite
                        ),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (newCommentText.isNotBlank()) {
                                onAddComment(newCommentText)
                                newCommentText = ""
                            }
                        },
                        enabled = newCommentText.isNotBlank(),
                        modifier = Modifier
                            .testTag("post_comment_btn")
                            .background(
                                if (newCommentText.isNotBlank()) CandyPink else DarkCleanSurfaceVariant,
                                CircleShape
                            )
                            .size(46.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Post comment",
                            tint = if (newCommentText.isNotBlank()) Color.White else TextMutedGray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Comments List
            if (comments.isEmpty()) {
                item {
                    Text(
                        text = "No comments yet. Be the first tactician to share your thoughts!",
                        color = TextMutedGray,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            } else {
                items(comments) { comment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkCleanSurface),
                        border = BorderStroke(0.5.dp, DarkCleanBorder)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = comment.authorName,
                                        color = TextPureWhite,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = DarkCleanSurfaceVariant,
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = comment.authorRank,
                                            color = ElectricCyan,
                                            fontSize = 10.sp,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable { onCommentLikeClick(comment) }
                                ) {
                                    Icon(
                                        imageVector = if (comment.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Like comment",
                                        tint = if (comment.isLiked) Color(0xFFF43F5E) else TextMutedGray,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${comment.likes}",
                                        color = if (comment.isLiked) Color(0xFFF43F5E) else TextMutedGray,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = comment.content,
                                color = TextSoftWhite,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
