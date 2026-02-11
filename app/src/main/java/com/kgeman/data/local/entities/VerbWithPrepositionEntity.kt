package com.kgeman.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kgeman.domain.models.VerbWithPreposition

/**
 * Room entity for caching verb with preposition data locally
 */
@Entity(tableName = "verbs_prepositions")
data class VerbWithPrepositionEntity(
    @PrimaryKey
    val id: String,
    val verb: String,
    val preposition: String,
    val case: String,
    val exampleSentence: String,
    val englishTranslation: String,
    val lastUpdated: Long = System.currentTimeMillis()
)

/**
 * Extension function to convert entity to domain model
 */
fun VerbWithPrepositionEntity.toDomainModel(): VerbWithPreposition {
    return VerbWithPreposition(
        id = id,
        verb = verb,
        preposition = preposition,
        case = case,
        exampleSentence = exampleSentence,
        englishTranslation = englishTranslation
    )
}

/**
 * Extension function to convert domain model to entity
 */
fun VerbWithPreposition.toEntity(): VerbWithPrepositionEntity {
    return VerbWithPrepositionEntity(
        id = id,
        verb = verb,
        preposition = preposition,
        case = case,
        exampleSentence = exampleSentence,
        englishTranslation = englishTranslation
    )
}
