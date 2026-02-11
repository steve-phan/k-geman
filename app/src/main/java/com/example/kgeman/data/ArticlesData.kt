package com.example.kgeman.data

import com.example.kgeman.model.Article
import com.example.kgeman.model.ArticleNoun
import com.example.kgeman.model.NounCategory

object ArticlesData {
    fun getArticleNouns(): List<ArticleNoun> {
        return listOf(
            // Der (masculine)
            ArticleNoun(
                noun = "Mann",
                article = Article.DER,
                plural = "Männer",
                translation = "man",
                category = NounCategory.PERSON
            ),
            ArticleNoun(
                noun = "Tisch",
                article = Article.DER,
                plural = "Tische",
                translation = "table",
                category = NounCategory.THING
            ),
            ArticleNoun(
                noun = "Stuhl",
                article = Article.DER,
                plural = "Stühle",
                translation = "chair",
                category = NounCategory.THING
            ),
            ArticleNoun(
                noun = "Hund",
                article = Article.DER,
                plural = "Hunde",
                translation = "dog",
                category = NounCategory.ANIMAL
            ),
            ArticleNoun(
                noun = "Baum",
                article = Article.DER,
                plural = "Bäume",
                translation = "tree",
                category = NounCategory.THING
            ),
            // Die (feminine)
            ArticleNoun(
                noun = "Frau",
                article = Article.DIE,
                plural = "Frauen",
                translation = "woman",
                category = NounCategory.PERSON
            ),
            ArticleNoun(
                noun = "Katze",
                article = Article.DIE,
                plural = "Katzen",
                translation = "cat",
                category = NounCategory.ANIMAL
            ),
            ArticleNoun(
                noun = "Blume",
                article = Article.DIE,
                plural = "Blumen",
                translation = "flower",
                category = NounCategory.THING
            ),
            ArticleNoun(
                noun = "Tür",
                article = Article.DIE,
                plural = "Türen",
                translation = "door",
                category = NounCategory.THING
            ),
            ArticleNoun(
                noun = "Stadt",
                article = Article.DIE,
                plural = "Städte",
                translation = "city",
                category = NounCategory.PLACE
            ),
            // Das (neuter)
            ArticleNoun(
                noun = "Kind",
                article = Article.DAS,
                plural = "Kinder",
                translation = "child",
                category = NounCategory.PERSON
            ),
            ArticleNoun(
                noun = "Haus",
                article = Article.DAS,
                plural = "Häuser",
                translation = "house",
                category = NounCategory.THING
            ),
            ArticleNoun(
                noun = "Buch",
                article = Article.DAS,
                plural = "Bücher",
                translation = "book",
                category = NounCategory.THING
            ),
            ArticleNoun(
                noun = "Auto",
                article = Article.DAS,
                plural = "Autos",
                translation = "car",
                category = NounCategory.THING
            ),
            ArticleNoun(
                noun = "Fenster",
                article = Article.DAS,
                plural = "Fenster",
                translation = "window",
                category = NounCategory.THING
            )
        )
    }
}
