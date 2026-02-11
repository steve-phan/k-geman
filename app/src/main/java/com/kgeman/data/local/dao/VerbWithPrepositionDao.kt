package com.kgeman.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kgeman.data.local.entities.VerbWithPrepositionEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for VerbWithPreposition entities
 */
@Dao
interface VerbWithPrepositionDao {
    
    @Query("SELECT * FROM verbs_prepositions ORDER BY verb ASC")
    fun getAllVerbs(): Flow<List<VerbWithPrepositionEntity>>
    
    @Query("SELECT * FROM verbs_prepositions WHERE id = :id")
    suspend fun getVerbById(id: String): VerbWithPrepositionEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(verbs: List<VerbWithPrepositionEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(verb: VerbWithPrepositionEntity)
    
    @Query("DELETE FROM verbs_prepositions")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM verbs_prepositions")
    suspend fun getCount(): Int
}
