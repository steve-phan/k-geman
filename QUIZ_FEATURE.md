# Quiz Feature Documentation

## Overview
The quiz feature allows users to test their German grammar knowledge through interactive multiple-choice questions.

## Features

### 1. Timer (Zeit-Anzeige)
- Displays elapsed time in MM:SS format
- Located at the top-right of the quiz screen
- Updates every second
- Starts when the quiz begins

### 2. Progress Bar (Fortschrittsbalken)
- Shows current question number out of total questions
- Visual progress bar indicating completion percentage
- Example: "Frage 5 von 10" (Question 5 of 10)

### 3. Navigation Controls
- **Back (Zurück)**: Navigate to the previous question
  - Disabled on the first question
- **Next (Weiter)**: Navigate to the next question
  - Disabled on the last question
- **Repeat (Wiederholen)**: Reset the current question
  - Clears answer selection and feedback

### 4. Hint System (Hinweis)
- Toggle button to show/hide hints
- Hints provide helpful context:
  - For verbs: Shows the grammatical case (Akkusativ/Dativ/Genitiv)
  - For articles: Shows the rule or pattern
  - For clauses: Shows the rule explanation
- Displayed in a highlighted card when shown

### 5. Question Types
The quiz generates questions from three content types:

#### Verb Questions
- Format: "Welche Präposition passt zu '[verb]'?"
- Tests knowledge of verb-preposition combinations
- Hint shows the grammatical case

#### Article Questions
- Format: "Welcher Artikel passt zu '[noun]'?"
- Tests knowledge of der/die/das articles
- Hint shows the grammatical rule

#### Clause Questions
- Format: "Welche Konjunktion verbindet die Sätze?"
- Tests knowledge of conjunctions
- Hint shows the clause rule explanation

### 6. Answer Feedback
After selecting an answer:
- **Correct Answer**: 
  - Green checkmark (✓ Richtig!)
  - Highlighted in primary color
  - Shows example sentence as explanation
- **Incorrect Answer**:
  - Red cross (✗ Falsch!)
  - Shows the correct answer
  - Highlights the correct option
  - Shows example sentence as explanation

### 7. Quiz Modes
Currently implemented:
- **Mixed Quiz**: Combines questions from all three content types
  - 5 verb questions
  - 5 article questions
  - 5 clause questions
  - Questions are shuffled

Future modes (can be added):
- Verb-only quiz
- Article-only quiz
- Clause-only quiz

## Technical Implementation

### Components

1. **QuizQuestion.kt**: Domain model for quiz questions
2. **QuizSession.kt**: Manages quiz state and progress
3. **QuizGenerator.kt**: Generates quiz questions from learning content
4. **QuizViewModel.kt**: Handles quiz logic and state management
5. **QuizFragment.kt**: UI implementation with timer and navigation
6. **fragment_quiz.xml**: Layout for quiz interface

### Data Flow

1. User navigates to Quiz tab in bottom navigation
2. QuizViewModel loads content from Room database
3. QuizGenerator creates randomized questions
4. QuizFragment displays first question
5. Timer starts automatically
6. User interacts with quiz (answer, hint, navigate)
7. ViewModel updates state and provides feedback
8. Fragment reflects state changes in UI

### Key Features

- **Offline-first**: Works with cached data from Room database
- **Reactive UI**: Uses LiveData for real-time updates
- **State management**: ViewModel survives configuration changes
- **Material Design 3**: Follows app's design system

## Usage

1. Open the K-Geman app
2. Tap the "Quiz" icon in the bottom navigation
3. Read the question
4. (Optional) Tap "Hinweis anzeigen" to see a hint
5. Select an answer from the multiple-choice options
6. View feedback on your answer
7. Use navigation buttons to move through questions
8. Monitor your time and progress as you go

## Future Enhancements

- Score tracking and history
- Different difficulty levels
- Timed challenges
- Achievement badges
- Share results
- Custom quiz creation
- Spaced repetition algorithm
