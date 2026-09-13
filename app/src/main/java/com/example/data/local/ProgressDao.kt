package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM student_progress WHERE id = 1")
    fun getProgress(): Flow<StudentProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProgress(progress: StudentProgressEntity)

    @Query("SELECT * FROM watched_items ORDER BY watchedTimestamp DESC")
    fun getAllWatchedItems(): Flow<List<WatchedItemEntity>>

    @Query("SELECT * FROM watched_items WHERE watchedTimestamp >= :afterTimestamp ORDER BY watchedTimestamp DESC")
    fun getWatchedItemsSince(afterTimestamp: Long): Flow<List<WatchedItemEntity>>

    @Query("SELECT * FROM watched_items WHERE watchedTimestamp >= :afterTimestamp ORDER BY watchedTimestamp DESC")
    suspend fun getWatchedItemsSinceDirect(afterTimestamp: Long): List<WatchedItemEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM watched_items WHERE contentId = :contentId)")
    fun isItemWatched(contentId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markWatched(item: WatchedItemEntity)

    @Query("SELECT * FROM completed_challenges ORDER BY completedTimestamp DESC")
    fun getCompletedChallenges(): Flow<List<CompletedChallengeEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM completed_challenges WHERE challengeDate = :date)")
    fun isChallengeCompletedToday(date: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markChallengeCompleted(challenge: CompletedChallengeEntity)
}
