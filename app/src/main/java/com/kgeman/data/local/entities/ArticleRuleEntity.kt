package com.kgeman.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kgeman.domain.models.ArticleRule

/**
 * Room entity for caching article rule data locally
 */
@Entity(tableName = "articles")
data class ArticleRuleEntity(
    @PrimaryKey
    val id: String,
    val noun: String,
    val article: String,
    val gender: String,
    val ruleHint: String,
    val example: String,
    val lastUpdated: Long = System.currentTimeMillis()
)

/**
 * Extension function to convert entity to domain model
 */
fun ArticleRuleEntity.toDomainModel(): ArticleRule {
    return ArticleRule(
        id = id,
        noun = noun,
        article = article,
        gender = gender,
        ruleHint = ruleHint,
        example = example
    )
}

/**
 * Extension function to convert domain model to entity
 */
fun ArticleRule.toEntity(): ArticleRuleEntity {
    return ArticleRuleEntity(
        id = id,
        noun = noun,
        article = article,
        gender = gender,
        ruleHint = ruleHint,
        example = example
    )
}
