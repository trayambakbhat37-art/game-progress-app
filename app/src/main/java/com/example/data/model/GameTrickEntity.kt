package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

class StringListConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        if (value.isNullOrBlank()) return emptyList()
        return value.split("\n---SPLIT---\n").filter { it.isNotBlank() }
    }

    @TypeConverter
    fun fromList(list: List<String>?): String {
        if (list.isNullOrEmpty()) return ""
        return list.joinToString("\n---SPLIT---\n")
    }
}

@Entity(tableName = "tricks")
@TypeConverters(StringListConverter::class)
data class GameTrickEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: String,
    val gameTitle: String,
    val levelName: String,
    val category: String, // "Boss Guide", "Level Walkthrough", "Shortcut", "Speedrun", "Puzzle", "Build"
    val difficulty: String, // "Easy", "Medium", "Hard", "Nightmare"
    val title: String,
    val summary: String,
    val steps: List<String>,
    val proTips: List<String>,
    val authorName: String,
    val authorRank: String,
    val upvotes: Int = 0,
    val isUpvoted: Boolean = false,
    val isSaved: Boolean = false,
    val isLevelCleared: Boolean = false,
    val estimatedMinutes: Int = 10,
    val commentsCount: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
