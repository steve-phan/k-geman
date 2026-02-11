package com.kgeman.domain.repository

import com.kgeman.data.local.KGemanDatabase
import com.kgeman.data.local.entities.toDomainModel
import com.kgeman.data.local.entities.toEntity
import com.kgeman.data.remote.FirestoreRepository
import com.kgeman.domain.models.ClauseExample
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository for ClauseExample data
 * Implements offline-first strategy: local cache first, then sync from Firestore
 */
class ClauseRepository(
    private val database: KGemanDatabase,
    private val firestoreRepository: FirestoreRepository
) {
    
    private val clauseDao = database.clauseExampleDao()
    
    /**
     * Get all clauses as Flow from local cache
     */
    fun getAllClauses(): Flow<List<ClauseExample>> {
        return clauseDao.getAllClauses().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    /**
     * Get clause by ID from local cache
     */
    suspend fun getClauseById(id: String): ClauseExample? {
        return clauseDao.getClauseById(id)?.toDomainModel()
    }
    
    /**
     * Sync clauses from Firestore and save to local cache
     */
    suspend fun syncFromFirestore(): Result<Unit> {
        return try {
            val clauses = firestoreRepository.getClauses()
            val entities = clauses.map { it.toEntity() }
            clauseDao.insertAll(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Check if local cache has data
     */
    suspend fun hasLocalData(): Boolean {
        return clauseDao.getCount() > 0
    }
}
