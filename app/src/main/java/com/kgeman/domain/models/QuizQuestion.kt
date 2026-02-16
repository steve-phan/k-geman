package com.kgeman.domain.models

/**
 * Domain model for quiz questions
 */
data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val hint: String,
    val explanation: String = ""
)

/**
 * Quiz session state
 */
data class QuizSession(
    val questions: List<QuizQuestion>,
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val startTime: Long = System.currentTimeMillis()
) {
    val currentQuestion: QuizQuestion?
        get() = questions.getOrNull(currentQuestionIndex)
    
    val progress: Float
        get() = if (questions.isEmpty()) 0f else (currentQuestionIndex + 1).toFloat() / questions.size
    
    val elapsedTimeSeconds: Long
        get() = (System.currentTimeMillis() - startTime) / 1000
    
    val isFinished: Boolean
        get() = currentQuestionIndex >= questions.size
}
