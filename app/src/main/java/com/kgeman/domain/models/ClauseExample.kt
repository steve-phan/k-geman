package com.kgeman.domain.models

/**
 * Domain model for clause examples
 * Demonstrates main and subordinate clauses in German
 */
data class ClauseExample(
    val id: String = "",
    val mainClause: String,
    val subordinateClause: String,
    val conjunction: String, // weil/dass/obwohl...
    val ruleExplanation: String
)
