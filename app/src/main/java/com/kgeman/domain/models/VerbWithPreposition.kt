package com.kgeman.domain.models

/**
 * Domain model for verbs with prepositions
 * Represents German verbs that require specific prepositions
 */
data class VerbWithPreposition(
    val id: String = "",
    val verb: String,
    val preposition: String,
    val case: String, // Akkusativ/Dativ/Genitiv
    val exampleSentence: String,
    val englishTranslation: String
)
