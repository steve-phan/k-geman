package com.example.kgeman.model

/**
 * Data model for German clauses (Haupt- und Nebensätze)
 */
data class ClauseExample(
    val type: ClauseType,
    val conjunction: String,
    val mainClause: String,
    val subordinateClause: String,
    val fullSentence: String,
    val translation: String,
    val explanation: String
)

enum class ClauseType {
    MAIN,           // Hauptsatz
    SUBORDINATE,    // Nebensatz
    COMPOUND        // Zusammengesetzter Satz
}
