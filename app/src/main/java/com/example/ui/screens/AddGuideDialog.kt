package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyPinkLight
import com.example.ui.theme.DarkCleanBorder
import com.example.ui.theme.DarkCleanSurface
import com.example.ui.theme.DarkCleanSurfaceVariant
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.SunshineYellow
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextPureWhite
import com.example.ui.theme.TextSoftWhite

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddGuideDialog(
    onDismiss: () -> Unit,
    onSubmit: (
        gameTitle: String,
        levelName: String,
        category: String,
        difficulty: String,
        title: String,
        summary: String,
        steps: List<String>,
        proTips: List<String>,
        estimatedMinutes: Int
    ) -> Unit
) {
    var gameTitle by remember { mutableStateOf("Fall Guys") }
    var levelName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Level Walkthrough") }
    var difficulty by remember { mutableStateOf("Medium") }
    var estimatedMinutesText by remember { mutableStateOf("5") }

    val categories = listOf("Level Walkthrough", "Boss Guide", "Shortcut", "Speedrun", "Puzzle")
    val difficulties = listOf("Easy", "Medium", "Hard", "Nightmare")
    val popularGames = listOf("Fall Guys", "Zelda: TotK", "Elden Ring", "Hollow Knight", "Cyberpunk 2077")

    val steps = remember { mutableStateListOf("Position your character at the edge", "Jump-dive to skip the hazard") }
    val tips = remember { mutableStateListOf("Diving mid-air cancels bounce stagger") }

    var stepInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.90f)
                .testTag("add_guide_dialog"),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = DarkCleanSurface),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CandyPink)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "SHARE GAMEPLAY TRICK",
                            color = CandyPinkLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Help Players Clear This Level",
                            color = TextPureWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_add_dialog_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextMutedGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    item {
                        // Quick game select chips
                        Text(
                            text = "Select or Enter Game",
                            color = TextSoftWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            popularGames.forEach { g ->
                                val isSelected = gameTitle.equals(g, ignoreCase = true)
                                Surface(
                                    modifier = Modifier.clickable { gameTitle = g },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) CandyPink else DarkCleanSurfaceVariant,
                                    border = androidx.compose.foundation.BorderStroke(
                                        0.5.dp,
                                        if (isSelected) Color.White else DarkCleanBorder
                                    )
                                ) {
                                    Text(
                                        text = g,
                                        color = if (isSelected) Color.White else TextSoftWhite,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = gameTitle,
                            onValueChange = { gameTitle = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("guide_game_input"),
                            label = { Text("Game Title") },
                            colors = customTextFieldColors()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = levelName,
                            onValueChange = { levelName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("guide_level_input"),
                            label = { Text("Level / Stage / Round Name") },
                            placeholder = { Text("e.g. Volleyfall, Slime Climb, Tip Toe") },
                            colors = customTextFieldColors()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Category Selector
                        Text(
                            text = "Category",
                            color = TextSoftWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            categories.forEach { cat ->
                                val isSelected = category == cat
                                Surface(
                                    modifier = Modifier.clickable { category = cat },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) ElectricCyan.copy(alpha = 0.2f) else DarkCleanSurfaceVariant,
                                    border = androidx.compose.foundation.BorderStroke(
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

                        Spacer(modifier = Modifier.height(12.dp))

                        // Difficulty Selector
                        Text(
                            text = "Difficulty",
                            color = TextSoftWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            difficulties.forEach { diff ->
                                val isSelected = difficulty == diff
                                Surface(
                                    modifier = Modifier.clickable { difficulty = diff },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) SunshineYellow.copy(alpha = 0.2f) else DarkCleanSurfaceVariant,
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        if (isSelected) SunshineYellow else DarkCleanBorder
                                    )
                                ) {
                                    Text(
                                        text = diff,
                                        color = if (isSelected) SunshineYellow else TextMutedGray,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("guide_title_input"),
                            label = { Text("Guide Title") },
                            placeholder = { Text("e.g. Center Court Spike Technique") },
                            colors = customTextFieldColors()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = summary,
                            onValueChange = { summary = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("guide_summary_input"),
                            label = { Text("Strategy Summary") },
                            placeholder = { Text("Briefly explain how this helps clear the round...") },
                            colors = customTextFieldColors(),
                            maxLines = 3
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = estimatedMinutesText,
                            onValueChange = { estimatedMinutesText = it.filter { c -> c.isDigit() } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("guide_minutes_input"),
                            label = { Text("Estimated Clear Time (Minutes)") },
                            colors = customTextFieldColors()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Step-by-Step Level Clearance Input
                        Text(
                            text = "Step-by-Step Instructions (${steps.size})",
                            color = TextPureWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    items(steps.size) { idx ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${idx + 1}.",
                                color = CandyPinkLight,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.width(24.dp)
                            )
                            Text(
                                text = steps[idx],
                                color = TextSoftWhite,
                                fontSize = 13.sp,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { steps.removeAt(idx) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete step",
                                    tint = TextMutedGray,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = stepInput,
                                onValueChange = { stepInput = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("step_input_field"),
                                placeholder = { Text("Add next clear step...") },
                                colors = customTextFieldColors(),
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            IconButton(
                                onClick = {
                                    if (stepInput.isNotBlank()) {
                                        steps.add(stepInput.trim())
                                        stepInput = ""
                                    }
                                },
                                modifier = Modifier
                                    .size(40.dp),
                                colors = androidx.compose.material3.IconButtonDefaults.iconButtonColors(
                                    containerColor = CandyPink,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add step"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Pro Tips input
                        Text(
                            text = "Pro Tips / Exploits (${tips.size})",
                            color = SunshineYellow,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    items(tips.size) { idx ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "💡",
                                modifier = Modifier.width(24.dp)
                            )
                            Text(
                                text = tips[idx],
                                color = Color(0xFFFEF3C7),
                                fontSize = 13.sp,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { tips.removeAt(idx) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete tip",
                                    tint = TextMutedGray,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = tipInput,
                                onValueChange = { tipInput = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("tip_input_field"),
                                placeholder = { Text("Add pro tip or mechanic exploit...") },
                                colors = customTextFieldColors(),
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            IconButton(
                                onClick = {
                                    if (tipInput.isNotBlank()) {
                                        tips.add(tipInput.trim())
                                        tipInput = ""
                                    }
                                },
                                modifier = Modifier.size(40.dp),
                                colors = androidx.compose.material3.IconButtonDefaults.iconButtonColors(
                                    containerColor = SunshineYellow,
                                    contentColor = Color.Black
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add tip"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Bottom Submit Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel", color = TextSoftWhite)
                    }

                    Button(
                        onClick = {
                            if (gameTitle.isNotBlank() && title.isNotBlank() && levelName.isNotBlank()) {
                                onSubmit(
                                    gameTitle,
                                    levelName,
                                    category,
                                    difficulty,
                                    title,
                                    summary.ifBlank { "Community verified trick for clearing $levelName." },
                                    if (steps.isEmpty()) listOf("Follow stage directions") else steps.toList(),
                                    tips.toList(),
                                    estimatedMinutesText.toIntOrNull() ?: 5
                                )
                                onDismiss()
                            }
                        },
                        enabled = gameTitle.isNotBlank() && title.isNotBlank() && levelName.isNotBlank(),
                        modifier = Modifier
                            .weight(1.5f)
                            .testTag("submit_guide_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CandyPink,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Publish (+150 XP)", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun customTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = CandyPink,
    unfocusedBorderColor = DarkCleanBorder,
    focusedContainerColor = DarkCleanSurfaceVariant,
    unfocusedContainerColor = DarkCleanSurfaceVariant,
    focusedTextColor = TextPureWhite,
    unfocusedTextColor = TextPureWhite,
    focusedLabelColor = CandyPinkLight,
    unfocusedLabelColor = TextMutedGray
)
