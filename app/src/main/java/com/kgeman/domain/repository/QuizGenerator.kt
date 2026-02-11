package com.kgeman.domain.repository

import com.kgeman.domain.models.QuizQuestion
import com.kgeman.domain.models.VerbWithPreposition
import com.kgeman.domain.models.ArticleRule
import com.kgeman.domain.models.ClauseExample

/**
 * Generates quiz questions from learning content
 */
object QuizGenerator {
    
    /**
     * Generate quiz questions from verbs
     */
    fun generateVerbQuizzes(verbs: List<VerbWithPreposition>): List<QuizQuestion> {
        return verbs.shuffled().take(10).map { verb ->
            val allPrepositions = verbs.map { it.preposition }.distinct().shuffled()
            val incorrectOptions = allPrepositions.filter { it != verb.preposition }.take(3)
            val options = (incorrectOptions + verb.preposition).shuffled()
            
            QuizQuestion(
                id = "verb_${verb.id}",
                question = "Welche Präposition passt zu '${verb.verb}'?",
                options = options,
                correctAnswer = verb.preposition,
                hint = "Kasus: ${verb.case}",
                explanation = verb.exampleSentence
            )
        }
    }
    
    /**
     * Generate quiz questions from articles
     */
    fun generateArticleQuizzes(articles: List<ArticleRule>): List<QuizQuestion> {
        return articles.shuffled().take(10).map { article ->
            val options = listOf("der", "die", "das").shuffled()
            
            QuizQuestion(
                id = "article_${article.id}",
                question = "Welcher Artikel passt zu '${article.noun}'?",
                options = options,
                correctAnswer = article.article,
                hint = article.ruleHint,
                explanation = article.example
            )
        }
    }
    
    /**
     * Generate quiz questions from clauses
     */
    fun generateClauseQuizzes(clauses: List<ClauseExample>): List<QuizQuestion> {
        return clauses.shuffled().take(10).map { clause ->
            val allConjunctions = clauses.map { it.conjunction }.distinct().shuffled()
            val incorrectOptions = allConjunctions.filter { it != clause.conjunction }.take(3)
            val options = (incorrectOptions + clause.conjunction).shuffled()
            
            QuizQuestion(
                id = "clause_${clause.id}",
                question = "Welche Konjunktion verbindet die Sätze?\n'${clause.mainClause}' ___ '${clause.subordinateClause}'",
                options = options,
                correctAnswer = clause.conjunction,
                hint = clause.ruleExplanation,
                explanation = "${clause.mainClause} ${clause.conjunction} ${clause.subordinateClause}"
            )
        }
    }
    
    /**
     * Generate mixed quiz questions from all content types
     */
    fun generateMixedQuizzes(
        verbs: List<VerbWithPreposition>,
        articles: List<ArticleRule>,
        clauses: List<ClauseExample>
    ): List<QuizQuestion> {
        val verbQuizzes = generateVerbQuizzes(verbs).take(5)
        val articleQuizzes = generateArticleQuizzes(articles).take(5)
        val clauseQuizzes = generateClauseQuizzes(clauses).take(5)
        
        return (verbQuizzes + articleQuizzes + clauseQuizzes).shuffled()
    }
}
