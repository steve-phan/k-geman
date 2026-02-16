package com.kgeman.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kgeman.data.local.KGemanDatabase
import com.kgeman.domain.models.QuizQuestion
import com.kgeman.domain.models.QuizSession
import com.kgeman.domain.repository.ArticleRepository
import com.kgeman.domain.repository.ClauseRepository
import com.kgeman.domain.repository.QuizGenerator
import com.kgeman.domain.repository.VerbRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel for quiz functionality
 */
class QuizViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = KGemanDatabase.getInstance(application)
    private val verbRepository = VerbRepository(database.verbDao())
    private val articleRepository = ArticleRepository(database.articleDao())
    private val clauseRepository = ClauseRepository(database.clauseDao())
    
    private val _quizSession = MutableLiveData<QuizSession>()
    val quizSession: LiveData<QuizSession> = _quizSession
    
    private val _currentTime = MutableLiveData<Long>(0)
    val currentTime: LiveData<Long> = _currentTime
    
    private val _showHint = MutableLiveData<Boolean>(false)
    val showHint: LiveData<Boolean> = _showHint
    
    private val _selectedAnswer = MutableLiveData<String?>()
    val selectedAnswer: LiveData<String?> = _selectedAnswer
    
    private val _isAnswerCorrect = MutableLiveData<Boolean?>()
    val isAnswerCorrect: LiveData<Boolean?> = _isAnswerCorrect
    
    /**
     * Start a new quiz session with mixed questions
     */
    fun startMixedQuiz() {
        viewModelScope.launch {
            try {
                val verbs = verbRepository.getAllVerbs().first()
                val articles = articleRepository.getAllArticles().first()
                val clauses = clauseRepository.getAllClauses().first()
                
                val questions = QuizGenerator.generateMixedQuizzes(verbs, articles, clauses)
                _quizSession.value = QuizSession(questions = questions)
                resetQuestion()
            } catch (e: Exception) {
                // Handle error - could show empty quiz or error message
                _quizSession.value = QuizSession(questions = emptyList())
            }
        }
    }
    
    /**
     * Start a quiz focused on verbs
     */
    fun startVerbQuiz() {
        viewModelScope.launch {
            try {
                val verbs = verbRepository.getAllVerbs().first()
                val questions = QuizGenerator.generateVerbQuizzes(verbs)
                _quizSession.value = QuizSession(questions = questions)
                resetQuestion()
            } catch (e: Exception) {
                _quizSession.value = QuizSession(questions = emptyList())
            }
        }
    }
    
    /**
     * Start a quiz focused on articles
     */
    fun startArticleQuiz() {
        viewModelScope.launch {
            try {
                val articles = articleRepository.getAllArticles().first()
                val questions = QuizGenerator.generateArticleQuizzes(articles)
                _quizSession.value = QuizSession(questions = questions)
                resetQuestion()
            } catch (e: Exception) {
                _quizSession.value = QuizSession(questions = emptyList())
            }
        }
    }
    
    /**
     * Start a quiz focused on clauses
     */
    fun startClauseQuiz() {
        viewModelScope.launch {
            try {
                val clauses = clauseRepository.getAllClauses().first()
                val questions = QuizGenerator.generateClauseQuizzes(clauses)
                _quizSession.value = QuizSession(questions = questions)
                resetQuestion()
            } catch (e: Exception) {
                _quizSession.value = QuizSession(questions = emptyList())
            }
        }
    }
    
    /**
     * Move to the next question
     */
    fun nextQuestion() {
        _quizSession.value?.let { session ->
            if (session.currentQuestionIndex < session.questions.size - 1) {
                _quizSession.value = session.copy(
                    currentQuestionIndex = session.currentQuestionIndex + 1
                )
                resetQuestion()
            }
        }
    }
    
    /**
     * Move to the previous question
     */
    fun previousQuestion() {
        _quizSession.value?.let { session ->
            if (session.currentQuestionIndex > 0) {
                _quizSession.value = session.copy(
                    currentQuestionIndex = session.currentQuestionIndex - 1
                )
                resetQuestion()
            }
        }
    }
    
    /**
     * Restart the current question
     */
    fun repeatQuestion() {
        resetQuestion()
    }
    
    /**
     * Toggle hint visibility
     */
    fun toggleHint() {
        _showHint.value = _showHint.value != true
    }
    
    /**
     * Select an answer
     */
    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
        val currentQuestion = _quizSession.value?.currentQuestion
        val isCorrect = answer == currentQuestion?.correctAnswer
        _isAnswerCorrect.value = isCorrect
        
        // Update score if correct
        if (isCorrect) {
            _quizSession.value?.let { session ->
                _quizSession.value = session.copy(score = session.score + 1)
            }
        }
    }
    
    /**
     * Update elapsed time
     */
    fun updateTime() {
        _quizSession.value?.let { session ->
            _currentTime.value = session.elapsedTimeSeconds
        }
    }
    
    /**
     * Reset question state when changing questions
     */
    private fun resetQuestion() {
        _showHint.value = false
        _selectedAnswer.value = null
        _isAnswerCorrect.value = null
    }
}
