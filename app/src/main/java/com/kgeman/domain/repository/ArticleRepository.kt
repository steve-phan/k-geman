package com.kgeman.domain.repository

import com.kgeman.data.local.KGemanDatabase
import com.kgeman.data.local.entities.toDomainModel
import com.kgeman.data.local.entities.toEntity
import com.kgeman.data.remote.FirestoreRepository
import com.kgeman.domain.models.ArticleRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository for ArticleRule data
 * Implements offline-first strategy: local cache first, then sync from Firestore
 */
class ArticleRepository(
    private val database: KGemanDatabase,
    private val firestoreRepository: FirestoreRepository
) {
    
    private val articleDao = database.articleRuleDao()
    
    /**
     * Get all articles as Flow from local cache
     */
    fun getAllArticles(): Flow<List<ArticleRule>> {
        return articleDao.getAllArticles().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    /**
     * Get article by ID from local cache
     */
    suspend fun getArticleById(id: String): ArticleRule? {
        return articleDao.getArticleById(id)?.toDomainModel()
    }
    
    /**
     * Sync articles from Firestore and save to local cache
     */
    suspend fun syncFromFirestore(): Result<Unit> {
        return try {
            val articles = firestoreRepository.getArticles()
            val entities = articles.map { it.toEntity() }
            articleDao.insertAll(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Check if local cache has data
     */
    suspend fun hasLocalData(): Boolean {
        return articleDao.getCount() > 0
    }
}
