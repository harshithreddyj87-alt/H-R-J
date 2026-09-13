package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_progress")
data class StudentProgressEntity(
    @PrimaryKey val id: Int = 1,
    val streakDays: Int = 3,
    val lastChallengeDate: String = "",
    val totalChallengesCompleted: Int = 2,
    val totalVideosWatched: Int = 5,
    val totalLessonsCompleted: Int = 3,
    val totalStoriesRead: Int = 4
)

@Entity(tableName = "watched_items")
data class WatchedItemEntity(
    @PrimaryKey val contentId: String,
    val title: String,
    val category: String,
    val watchedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "completed_challenges")
data class CompletedChallengeEntity(
    @PrimaryKey val challengeDate: String,
    val challengeText: String,
    val completedTimestamp: Long = System.currentTimeMillis()
)
