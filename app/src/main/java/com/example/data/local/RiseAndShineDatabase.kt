package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        StudentProgressEntity::class,
        WatchedItemEntity::class,
        CompletedChallengeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RiseAndShineDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao

    companion object {
        @Volatile
        private var INSTANCE: RiseAndShineDatabase? = null

        fun getInstance(context: Context): RiseAndShineDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RiseAndShineDatabase::class.java,
                    "rise_and_shine.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
