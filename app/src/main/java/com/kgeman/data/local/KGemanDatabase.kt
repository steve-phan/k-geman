package com.kgeman.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kgeman.data.local.dao.ArticleRuleDao
import com.kgeman.data.local.dao.ClauseExampleDao
import com.kgeman.data.local.dao.VerbWithPrepositionDao
import com.kgeman.data.local.entities.ArticleRuleEntity
import com.kgeman.data.local.entities.ClauseExampleEntity
import com.kgeman.data.local.entities.VerbWithPrepositionEntity

/**
 * Room Database for K-Geman app
 * Provides offline caching for German grammar content
 */
@Database(
    entities = [
        VerbWithPrepositionEntity::class,
        ArticleRuleEntity::class,
        ClauseExampleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class KGemanDatabase : RoomDatabase() {
    
    abstract fun verbWithPrepositionDao(): VerbWithPrepositionDao
    abstract fun articleRuleDao(): ArticleRuleDao
    abstract fun clauseExampleDao(): ClauseExampleDao
    
    companion object {
        @Volatile
        private var INSTANCE: KGemanDatabase? = null
        
        fun getDatabase(context: Context): KGemanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KGemanDatabase::class.java,
                    "kgeman_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
