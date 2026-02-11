package com.example.kgeman.model

/**
 * Data model for German articles (der/die/das)
 */
data class ArticleNoun(
    val noun: String,
    val article: Article,
    val plural: String,
    val translation: String,
    val category: NounCategory? = null
)

enum class Article {
    DER,    // masculine
    DIE,    // feminine
    DAS     // neuter
}

enum class NounCategory {
    PERSON,
    ANIMAL,
    THING,
    PLACE,
    ABSTRACT
}
