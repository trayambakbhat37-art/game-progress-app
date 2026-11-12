package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameTrickEntity
import com.example.ui.components.GuideCard
import com.example.ui.theme.DarkCleanBorder
import com.example.ui.theme.DarkCleanSurface
import com.example.ui.theme.SunshineYellow
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextPureWhite
import com.example.ui.theme.TextSoftWhite

@Composable
fun SavedGuidesScreen(
    savedTricks: List<GameTrickEntity>,
    onTrickClick: (GameTrickEntity) -> Unit,
    onUpvoteClick: (GameTrickEntity) -> Unit,
    onBookmarkClick: (GameTrickEntity) -> Unit,
    onClearToggleClick: (GameTrickEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("saved_guides_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(bottom = 12.dp)) {
                Text(
                    text = "SAVED PLAYBOOK",
                    color = SunshineYellow,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Quick Reference Tactics",
                    color = TextPureWhite,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "${savedTricks.size} bookmarked guides ready for your gaming session",
                    color = TextSoftWhite,
                    fontSize = 13.sp
                )
            }
        }

        if (savedTricks.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkCleanSurface),
                    border = BorderStroke(0.5.dp, DarkCleanBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🔖 No Saved Guides Yet",
                            color = TextPureWhite,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap the bookmark icon on any guide to pin it here for instant access during your matches.",
                            color = TextMutedGray,
                            fontSize = 13.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(savedTricks, key = { it.id }) { trick ->
                Box(modifier = Modifier.padding(vertical = 6.dp)) {
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

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
