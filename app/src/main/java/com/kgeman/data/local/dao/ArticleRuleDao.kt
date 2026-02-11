package com.kgeman.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kgeman.data.local.entities.ArticleRuleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for ArticleRule entities
 */
@Dao
interface ArticleRuleDao {
    
    @Query("SELECT * FROM articles ORDER BY noun ASC")
    fun getAllArticles(): Flow<List<ArticleRuleEntity>>
    
    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getArticleById(id: String): ArticleRuleEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleRuleEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleRuleEntity)
    
    @Query("DELETE FROM articles")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM articles")
    suspend fun getCount(): Int
}
