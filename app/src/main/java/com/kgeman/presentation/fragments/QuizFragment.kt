package com.kgeman.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.radiobutton.MaterialRadioButton
import com.kgeman.R
import com.kgeman.databinding.FragmentQuizBinding
import com.kgeman.domain.models.QuizQuestion
import com.kgeman.presentation.viewmodels.QuizViewModel

/**
 * Fragment for quiz functionality with timer, progress bar, and navigation
 */
class QuizFragment : Fragment() {
    
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: QuizViewModel by viewModels()
    
    private val handler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            viewModel.updateTime()
            handler.postDelayed(this, 1000) // Update every second
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupListeners()
        observeData()
        
        // Start the quiz
        viewModel.startMixedQuiz()
        
        // Start timer
        handler.post(timerRunnable)
    }
    
    private fun setupListeners() {
        // Hint button
        binding.hintButton.setOnClickListener {
            viewModel.toggleHint()
        }
        
        // Navigation buttons
        binding.backButton.setOnClickListener {
            viewModel.previousQuestion()
        }
        
        binding.nextButton.setOnClickListener {
            viewModel.nextQuestion()
        }
        
        binding.repeatButton.setOnClickListener {
            viewModel.repeatQuestion()
        }
        
        // Answer selection
        binding.answersGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedButton = view?.findViewById<MaterialRadioButton>(checkedId)
            selectedButton?.text?.toString()?.let { answer ->
                viewModel.selectAnswer(answer)
            }
        }
    }
    
    private fun observeData() {
        // Observe quiz session
        viewModel.quizSession.observe(viewLifecycleOwner) { session ->
            session.currentQuestion?.let { question ->
                displayQuestion(question)
                updateProgress(session.currentQuestionIndex + 1, session.questions.size)
                updateNavigationButtons(session.currentQuestionIndex, session.questions.size)
            }
        }
        
        // Observe timer
        viewModel.currentTime.observe(viewLifecycleOwner) { seconds ->
            val minutes = seconds / 60
            val secs = seconds % 60
            binding.timerText.text = String.format("%02d:%02d", minutes, secs)
        }
        
        // Observe hint visibility
        viewModel.showHint.observe(viewLifecycleOwner) { show ->
            binding.hintCard.visibility = if (show) View.VISIBLE else View.GONE
            binding.hintButton.text = if (show) "Hinweis verbergen" else "Hinweis anzeigen"
        }
        
        // Observe selected answer
        viewModel.selectedAnswer.observe(viewLifecycleOwner) { _ ->
            // Selected answer is being tracked
        }
        
        // Observe answer correctness
        viewModel.isAnswerCorrect.observe(viewLifecycleOwner) { isCorrect ->
            if (isCorrect != null) {
                showFeedback(isCorrect)
            } else {
                hideFeedback()
            }
        }
    }
    
    private fun displayQuestion(question: QuizQuestion) {
        binding.questionText.text = question.question
        binding.hintText.text = "💡 ${question.hint}"
        
        // Set up answer options
        val options = question.options
        val radioButtons = listOf(
            binding.option1,
            binding.option2,
            binding.option3,
            binding.option4
        )
        
        // Clear previous selection
        binding.answersGroup.clearCheck()
        
        // Reset button colors
        radioButtons.forEach { button ->
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_on_surface))
            button.isEnabled = true
        }
        
        // Display options
        options.forEachIndexed { index, option ->
            if (index < radioButtons.size) {
                radioButtons[index].text = option
                radioButtons[index].visibility = View.VISIBLE
            }
        }
        
        // Hide unused options
        for (i in options.size until radioButtons.size) {
            radioButtons[i].visibility = View.GONE
        }
    }
    
    private fun updateProgress(current: Int, total: Int) {
        binding.progressText.text = "Frage $current von $total"
        val progress = (current.toFloat() / total.toFloat() * 100).toInt()
        binding.progressBar.progress = progress
    }
    
    private fun updateNavigationButtons(currentIndex: Int, totalQuestions: Int) {
        binding.backButton.isEnabled = currentIndex > 0
        binding.nextButton.isEnabled = currentIndex < totalQuestions - 1
    }
    
    private fun showFeedback(isCorrect: Boolean) {
        binding.feedbackCard.visibility = View.VISIBLE
        
        val question = viewModel.quizSession.value?.currentQuestion
        
        if (isCorrect) {
            binding.feedbackText.text = "✓ Richtig!"
            binding.feedbackText.setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_primary))
            binding.feedbackCard.setCardBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.md_theme_primary_container)
            )
        } else {
            binding.feedbackText.text = "✗ Falsch! Richtige Antwort: ${question?.correctAnswer}"
            binding.feedbackText.setTextColor(ContextCompat.getColor(requireContext(), R.color.quiz_error))
            binding.feedbackCard.setCardBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.quiz_error_background)
            )
        }
        
        binding.explanationText.text = question?.explanation ?: ""
        binding.explanationText.visibility = if (question?.explanation.isNullOrEmpty()) View.GONE else View.VISIBLE
        
        // Highlight the correct answer
        highlightCorrectAnswer(question?.correctAnswer, question?.options)
    }
    
    private fun hideFeedback() {
        binding.feedbackCard.visibility = View.GONE
    }
    
    private fun highlightCorrectAnswer(correctAnswer: String?, options: List<String>?) {
        if (correctAnswer == null || options == null) return
        
        val radioButtons = listOf(
            binding.option1,
            binding.option2,
            binding.option3,
            binding.option4
        )
        
        radioButtons.forEach { button ->
            if (button.text.toString() == correctAnswer) {
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_primary))
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(timerRunnable)
        _binding = null
    }
}
