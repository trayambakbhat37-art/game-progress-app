package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_progress")
data class GameProgressEntity(
    @PrimaryKey
    val gameId: String,
    val gameTitle: String,
    val tagline: String = "",
    val genre: String,
    val currentStage: String = "Stage 1",
    val difficulty: String = "Medium",
    val description: String = "",
    val keyMechanics: String = "", // Delimited by "|"
    val recommendedTips: String = "", // Delimited by "|"
    val accentColorHex: String = "#FF2A85",
    val totalKnownLevels: Int = 10,
    val clearedCount: Int = 0,
    val isCurrentActive: Boolean = false,
    val isFavorite: Boolean = false
) {
    fun getKeyMechanicsList(): List<String> {
        if (keyMechanics.isBlank()) return emptyList()
        return keyMechanics.split("|").filter { it.isNotBlank() }
    }

    fun getRecommendedTipsList(): List<String> {
        if (recommendedTips.isBlank()) return emptyList()
        return recommendedTips.split("|").filter { it.isNotBlank() }
    }
}
