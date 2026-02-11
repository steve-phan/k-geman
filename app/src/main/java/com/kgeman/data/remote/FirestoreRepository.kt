package com.kgeman.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.kgeman.domain.models.ArticleRule
import com.kgeman.domain.models.ClauseExample
import com.kgeman.domain.models.VerbWithPreposition
import kotlinx.coroutines.tasks.await

/**
 * Repository for fetching grammar content from Firestore
 * Collections: verbs_prepositions, articles, clauses
 */
class FirestoreRepository {
    
    private val firestore = FirebaseFirestore.getInstance()
    
    /**
     * Fetch all verbs with prepositions from Firestore
     */
    suspend fun getVerbs(): List<VerbWithPreposition> {
        return try {
            val snapshot = firestore.collection("verbs_prepositions")
                .get()
                .await()
            
            snapshot.documents.mapNotNull { doc ->
                try {
                    VerbWithPreposition(
                        id = doc.id,
                        verb = doc.getString("verb") ?: "",
                        preposition = doc.getString("preposition") ?: "",
                        case = doc.getString("case") ?: "",
                        exampleSentence = doc.getString("example_de") ?: "",
                        englishTranslation = doc.getString("example_en") ?: ""
                    )
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Fetch all article rules from Firestore
     */
    suspend fun getArticles(): List<ArticleRule> {
        return try {
            val snapshot = firestore.collection("articles")
                .get()
                .await()
            
            snapshot.documents.mapNotNull { doc ->
                try {
                    ArticleRule(
                        id = doc.id,
                        noun = doc.getString("noun") ?: "",
                        article = doc.getString("article") ?: "",
                        gender = doc.getString("gender") ?: "",
                        ruleHint = doc.getString("rule_hint") ?: "",
                        example = doc.getString("example") ?: ""
                    )
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Fetch all clause examples from Firestore
     */
    suspend fun getClauses(): List<ClauseExample> {
        return try {
            val snapshot = firestore.collection("clauses")
                .get()
                .await()
            
            snapshot.documents.mapNotNull { doc ->
                try {
                    ClauseExample(
                        id = doc.id,
                        mainClause = doc.getString("main_clause") ?: "",
                        subordinateClause = doc.getString("sub_clause") ?: "",
                        conjunction = doc.getString("conjunction") ?: "",
                        ruleExplanation = doc.getString("rule_explanation") ?: ""
                    )
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
