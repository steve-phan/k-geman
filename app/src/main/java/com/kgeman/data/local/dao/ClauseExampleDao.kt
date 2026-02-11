package com.kgeman.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kgeman.data.local.entities.ClauseExampleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for ClauseExample entities
 */
@Dao
interface ClauseExampleDao {
    
    @Query("SELECT * FROM clauses ORDER BY conjunction ASC")
    fun getAllClauses(): Flow<List<ClauseExampleEntity>>
    
    @Query("SELECT * FROM clauses WHERE id = :id")
    suspend fun getClauseById(id: String): ClauseExampleEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(clauses: List<ClauseExampleEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clause: ClauseExampleEntity)
    
    @Query("DELETE FROM clauses")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM clauses")
    suspend fun getCount(): Int
}
