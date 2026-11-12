package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.dao.CommentDao
import com.example.data.dao.GameProgressDao
import com.example.data.dao.TrickDao
import com.example.data.model.CommentEntity
import com.example.data.model.GameProgressEntity
import com.example.data.model.GameTrickEntity
import com.example.data.model.StringListConverter

@Database(
    entities = [GameTrickEntity::class, CommentEntity::class, GameProgressEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class GameTacticsDatabase : RoomDatabase() {
    abstract fun trickDao(): TrickDao
    abstract fun commentDao(): CommentDao
    abstract fun gameProgressDao(): GameProgressDao

    companion object {
        @Volatile
        private var INSTANCE: GameTacticsDatabase? = null

        fun getDatabase(context: Context): GameTacticsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameTacticsDatabase::class.java,
                    "gametactics_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
