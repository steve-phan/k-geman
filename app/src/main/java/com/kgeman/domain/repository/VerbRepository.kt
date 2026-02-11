package com.kgeman.domain.repository

import com.kgeman.data.local.KGemanDatabase
import com.kgeman.data.local.entities.toDomainModel
import com.kgeman.data.local.entities.toEntity
import com.kgeman.data.remote.FirestoreRepository
import com.kgeman.domain.models.VerbWithPreposition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository for VerbWithPreposition data
 * Implements offline-first strategy: local cache first, then sync from Firestore
 */
class VerbRepository(
    private val database: KGemanDatabase,
    private val firestoreRepository: FirestoreRepository
) {
    
    private val verbDao = database.verbWithPrepositionDao()
    
    /**
     * Get all verbs as Flow from local cache
     */
    fun getAllVerbs(): Flow<List<VerbWithPreposition>> {
        return verbDao.getAllVerbs().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    /**
     * Get verb by ID from local cache
     */
    suspend fun getVerbById(id: String): VerbWithPreposition? {
        return verbDao.getVerbById(id)?.toDomainModel()
    }
    
    /**
     * Sync verbs from Firestore and save to local cache
     */
    suspend fun syncFromFirestore(): Result<Unit> {
        return try {
            val verbs = firestoreRepository.getVerbs()
            val entities = verbs.map { it.toEntity() }
            verbDao.insertAll(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Check if local cache has data
     */
    suspend fun hasLocalData(): Boolean {
        return verbDao.getCount() > 0
    }
}
