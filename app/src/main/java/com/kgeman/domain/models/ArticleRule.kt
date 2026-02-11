package com.kgeman.domain.models

/**
 * Domain model for German article rules
 * Helps learners understand der/die/das article assignment
 */
data class ArticleRule(
    val id: String = "",
    val noun: String,
    val article: String, // der/die/das
    val gender: String, // Maskulin/Feminin/Neutrum
    val ruleHint: String,
    val example: String
)
