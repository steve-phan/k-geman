package com.example.kgeman.model

/**
 * Data model for German verbs with prepositions (Verben mit Präpositionen)
 */
data class VerbWithPreposition(
    val verb: String,
    val preposition: String,
    val case: GrammaticalCase,
    val example: String,
    val translation: String
)

enum class GrammaticalCase {
    ACCUSATIVE,  // Akkusativ
    DATIVE,      // Dativ
    GENITIVE     // Genitiv
}
