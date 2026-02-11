package com.kgeman.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kgeman.domain.models.ClauseExample

/**
 * Room entity for caching clause example data locally
 */
@Entity(tableName = "clauses")
data class ClauseExampleEntity(
    @PrimaryKey
    val id: String,
    val mainClause: String,
    val subordinateClause: String,
    val conjunction: String,
    val ruleExplanation: String,
    val lastUpdated: Long = System.currentTimeMillis()
)

/**
 * Extension function to convert entity to domain model
 */
fun ClauseExampleEntity.toDomainModel(): ClauseExample {
    return ClauseExample(
        id = id,
        mainClause = mainClause,
        subordinateClause = subordinateClause,
        conjunction = conjunction,
        ruleExplanation = ruleExplanation
    )
}

/**
 * Extension function to convert domain model to entity
 */
fun ClauseExample.toEntity(): ClauseExampleEntity {
    return ClauseExampleEntity(
        id = id,
        mainClause = mainClause,
        subordinateClause = subordinateClause,
        conjunction = conjunction,
        ruleExplanation = ruleExplanation
    )
}
