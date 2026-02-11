package com.kgeman.data

import android.content.Context
import android.content.SharedPreferences
import androidx.work.*
import com.kgeman.data.local.KGemanDatabase
import com.kgeman.data.remote.FirestoreRepository
import com.kgeman.domain.repository.ArticleRepository
import com.kgeman.domain.repository.ClauseRepository
import com.kgeman.domain.repository.VerbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

/**
 * Manages synchronization between Firestore and Room database
 * Handles initial sync and periodic weekly updates
 */
class SyncManager(private val context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences("sync_prefs", Context.MODE_PRIVATE)
    private val database = KGemanDatabase.getDatabase(context)
    private val firestoreRepository = FirestoreRepository()
    
    private val verbRepository = VerbRepository(database, firestoreRepository)
    private val articleRepository = ArticleRepository(database, firestoreRepository)
    private val clauseRepository = ClauseRepository(database, firestoreRepository)
    
    companion object {
        private const val LAST_SYNC_KEY = "last_sync_timestamp"
        private const val SYNC_WORK_NAME = "firestore_sync_work"
        private const val SYNC_INTERVAL_DAYS = 7L
    }
    
    /**
     * Synchronize all data from Firestore to Room
     * Called on first launch or manually triggered
     */
    suspend fun syncFirestoreToRoom(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // Sync all content types
            val verbResult = verbRepository.syncFromFirestore()
            val articleResult = articleRepository.syncFromFirestore()
            val clauseResult = clauseRepository.syncFromFirestore()
            
            // Check if all syncs succeeded
            if (verbResult.isSuccess && articleResult.isSuccess && clauseResult.isSuccess) {
                // Update last sync timestamp
                prefs.edit().putLong(LAST_SYNC_KEY, System.currentTimeMillis()).apply()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Sync failed for one or more content types"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Check if initial sync is needed (no local data exists)
     */
    suspend fun needsInitialSync(): Boolean = withContext(Dispatchers.IO) {
        !verbRepository.hasLocalData() || 
        !articleRepository.hasLocalData() || 
        !clauseRepository.hasLocalData()
    }
    
    /**
     * Get last sync timestamp
     */
    fun getLastSyncTime(): Long {
        return prefs.getLong(LAST_SYNC_KEY, 0L)
    }
    
    /**
     * Schedule periodic weekly sync using WorkManager
     */
    fun scheduleWeeklySync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            SYNC_INTERVAL_DAYS,
            TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
        
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }
    
    /**
     * Cancel scheduled sync
     */
    fun cancelScheduledSync() {
        WorkManager.getInstance(context).cancelUniqueWork(SYNC_WORK_NAME)
    }
}

/**
 * WorkManager Worker for background sync
 */
class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {
        val syncManager = SyncManager(applicationContext)
        return try {
            val syncResult = syncManager.syncFirestoreToRoom()
            if (syncResult.isSuccess) {
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
