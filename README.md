# K-Geman - German Grammar Learning App

An Android application built with Kotlin to help learners study German grammar, focusing on:
- **Verben mit Präpositionen** (Verbs with Prepositions)
- **Artikel** (der/die/das)
- **Haupt- und Nebensätze** (Main and Subordinate Clauses)

## Architecture

The app follows **MVVM with Clean Architecture** principles:

```
app/
├── data/
│   ├── local/          # Room Database (offline cache)
│   │   ├── dao/        # Data Access Objects
│   │   ├── entities/   # Room entities
│   │   └── KGemanDatabase.kt
│   ├── remote/         # Firestore repository
│   └── SyncManager.kt  # Sync logic with WorkManager
├── domain/
│   ├── models/         # Domain models
│   └── repository/     # Repository interfaces
└── presentation/
    ├── fragments/      # UI fragments
    ├── adapters/       # RecyclerView adapters
    └── viewmodels/     # ViewModels
```

## Tech Stack

- **Language:** Kotlin
- **Architecture:** MVVM + Clean Architecture
- **UI:** XML Layouts + ViewBinding (Material 3)
- **Database:** 
  - Firebase Firestore (primary source)
  - Room Database 2.6.1 (local cache)
- **Async:** Coroutines + Flow
- **Background Tasks:** WorkManager (weekly sync)
- **Dependencies Management:** Gradle with Kotlin DSL

## Features

### 1. Offline-First Architecture
- Content downloaded from Firestore on first launch
- Saved to Room Database for offline access
- Weekly sync for content updates via WorkManager

### 2. Four Learning Sections
- **Verbs:** Learn German verbs with required prepositions and cases
- **Articles:** Master der/die/das article rules with examples
- **Clauses:** Understand main and subordinate clause structures
- **Quiz:** Test your knowledge with interactive multiple-choice questions

### 3. Interactive Quiz Mode
- Timer to track quiz duration
- Progress bar showing completion status
- Navigation controls (Back, Next, Repeat)
- Contextual hints for each question
- Immediate feedback on answers
- Mixed questions from all content types

### 4. Material 3 Design
- Bottom navigation for easy section switching (Verbs, Articles, Clauses, Quiz)
- Card-based list views
- Offline indicator when no internet connection

### 5. Privacy-Focused
- **Zero personal data collection**
- No user authentication required
- Read-only Firestore access
- Google Play compliant

## Firebase Setup

### Firestore Collections Structure

#### verbs_prepositions/{id}
```json
{
  "verb": "denken",
  "preposition": "an",
  "case": "Akkusativ",
  "example_de": "Ich denke an dich.",
  "example_en": "I'm thinking of you."
}
```

#### articles/{id}
```json
{
  "noun": "Tisch",
  "article": "der",
  "gender": "Maskulin",
  "rule_hint": "Most nouns ending in -isch are masculine",
  "example": "der Tisch (the table)"
}
```

#### clauses/{id}
```json
{
  "main_clause": "Ich bleibe zu Hause",
  "sub_clause": "weil es regnet",
  "conjunction": "weil",
  "rule_explanation": "The verb moves to the end in subordinate clauses with 'weil'"
}
```

### Security Rules

The `firestore.rules` file provides read-only public access:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /verbs_prepositions/{document=**} {
      allow read: if true;
      allow write: if false;
    }
    // Similar for articles and clauses
  }
}
```

## Installation

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17
- Android SDK 24+ (minimum)
- Firebase project (free tier)

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/steve-phan/k-geman.git
   cd k-geman
   ```

2. **Set up Firebase**
   - Create a Firebase project at [console.firebase.google.com](https://console.firebase.google.com)
   - Add an Android app with package name: `com.kgeman`
   - Download `google-services.json` and replace the placeholder in `app/`
   - Enable Firestore Database
   - Deploy security rules: `firebase deploy --only firestore:rules`

3. **Add sample data to Firestore**
   - Use Firebase Console to add documents to collections:
     - `verbs_prepositions`
     - `articles`
     - `clauses`

4. **Build and run**
   ```bash
   ./gradlew assembleDebug
   # Or open in Android Studio and click Run
   ```

## Dependencies

Key dependencies (from `app/build.gradle.kts`):

```kotlin
// Firebase BOM 32.7.0
implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
implementation("com.google.firebase:firebase-firestore-ktx")

// Room 2.6.1
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// WorkManager
implementation("androidx.work:work-runtime-ktx:2.9.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

## Google Play Compliance

### Permissions
- `INTERNET`: Required for Firestore sync
- `ACCESS_NETWORK_STATE`: For offline detection

### Data Safety Form
- **Data Collection:** None
- **Data Sharing:** None
- **Security:** No encryption needed (no user data)
- **Account Required:** No

### Privacy Policy
See [PRIVACY_POLICY.md](PRIVACY_POLICY.md)

## Quiz Feature

For detailed documentation about the quiz feature, see:
- [QUIZ_FEATURE.md](QUIZ_FEATURE.md) - Feature documentation and implementation details
- [QUIZ_UI_MOCKUP.md](QUIZ_UI_MOCKUP.md) - UI design and interaction guide

## Project Structure Details

### Data Flow

1. **Initial Launch:**
   ```
   MainActivity → SyncManager.needsInitialSync()
   → FirestoreRepository.getVerbs/Articles/Clauses()
   → Room DAO.insertAll()
   → Local cache ready
   ```

2. **Loading Content:**
   ```
   Fragment → ViewModel → Repository.getAll()
   → Room DAO (Flow) → LiveData → RecyclerView Adapter
   ```

3. **Weekly Sync:**
   ```
   WorkManager (scheduled) → SyncWorker.doWork()
   → SyncManager.syncFirestoreToRoom()
   → Update Room cache
   ```

### Key Classes

- **SyncManager**: Orchestrates Firestore ↔ Room synchronization
- **KGemanDatabase**: Room database singleton
- **FirestoreRepository**: Fetches data from Firestore
- **[Type]Repository**: Unified repository with offline-first logic
- **MainActivity**: Handles navigation and initial sync
- **[Type]Fragment**: UI for each content section
- **[Type]Adapter**: RecyclerView adapters with DiffUtil

## Development Notes

### Adding New Content Types

1. Create domain model in `domain/models/`
2. Create Room entity in `data/local/entities/`
3. Create DAO in `data/local/dao/`
4. Update `KGemanDatabase` to include new entity
5. Add Firestore fetch method to `FirestoreRepository`
6. Create unified repository
7. Create ViewModel, Adapter, Fragment
8. Add to bottom navigation

### Testing with Empty Firestore

The app gracefully handles empty collections:
- Shows "Keine Daten verfügbar" (No data available) message
- Offline indicator appears when no internet
- Retry sync on next app launch

## Future Enhancements

- [ ] Audio pronunciation (TTS or pre-recorded)
- [ ] Search functionality
- [ ] Favorites/bookmarks
- [x] Quiz mode ✅ (Implemented!)
- [ ] Quiz score tracking and history
- [ ] Progress tracking
- [ ] Dark mode support

## License

MIT License - See LICENSE file for details

## Contributing

Contributions welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Follow existing code style (Kotlin conventions)
4. Submit a pull request

## Support

For issues or questions:
- GitHub Issues: [https://github.com/steve-phan/k-geman/issues](https://github.com/steve-phan/k-geman/issues)
- Email: [your-email@example.com]

---

**Built with ❤️ for German language learners**