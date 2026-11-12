package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CandyPink
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.SunshineYellow
import com.example.ui.theme.VividVioletLight

@Composable
fun CategoryBadge(
    category: String,
    modifier: Modifier = Modifier
) {
    val (icon, color) = when (category.lowercase()) {
        "boss guide" -> Icons.Default.EmojiEvents to SunshineYellow
        "level walkthrough" -> Icons.Default.Explore to ElectricCyan
        "speedrun" -> Icons.Default.Timer to CandyPink
        "shortcut", "secret shortcut" -> Icons.Default.Bolt to VividVioletLight
        "puzzle" -> Icons.Default.Lightbulb to Color(0xFF38BDF8)
        else -> Icons.Default.Build to ElectricCyan
    }

    Row(
        modifier = modifier
            .background(color.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
            .padding(horizontal = 7.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = category,
            tint = color,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = category,
            color = color,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
