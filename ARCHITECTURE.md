# K-Geman App - Technical Architecture Document

## Overview
K-Geman is an Android application for German grammar learning, built with MVVM architecture and Clean Architecture principles, featuring offline-first data synchronization.

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         PRESENTATION LAYER                       │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────┐     ┌──────────────┐     ┌──────────────┐   │
│  │  MainActivity │────▶│BottomNavView │────▶│   Fragments  │   │
│  └──────────────┘     └──────────────┘     └──────┬───────┘   │
│         │                                           │            │
│         │              ┌────────────────────────────┴──────┐    │
│         │              │                                    │    │
│         │         ┌────▼──────┐  ┌──────────┐  ┌──────────▼─┐  │
│         │         │VerbsFragment│ ArticlesFragment ClausesFragment
│         │         └────┬──────┘  └────┬─────┘  └──────┬─────┘  │
│         │              │              │               │         │
│    ┌────▼──────────────▼──────────────▼───────────────▼─────┐  │
│    │                    ViewModels                           │  │
│    │  ┌────────────┐  ┌──────────────┐  ┌──────────────┐   │  │
│    │  │VerbsViewModel│ ArticlesViewModel ClausesViewModel│   │  │
│    │  └─────┬──────┘  └──────┬───────┘  └──────┬───────┘   │  │
│    └────────┼─────────────────┼──────────────────┼───────────┘  │
│             │                 │                  │               │
│    ┌────────▼─────────────────▼──────────────────▼───────────┐  │
│    │                   Adapters (RecyclerView)               │  │
│    │  VerbsAdapter  │  ArticlesAdapter  │  ClausesAdapter   │  │
│    └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                                │
                                │ LiveData / Flow
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                          DOMAIN LAYER                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                     Models (Data Classes)                 │  │
│  │  ┌──────────────────┐  ┌────────────┐  ┌──────────────┐ │  │
│  │  │VerbWithPreposition  │ArticleRule │  │ClauseExample │ │  │
│  │  └──────────────────┘  └────────────┘  └──────────────┘ │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                      Repositories                         │  │
│  │  ┌──────────────┐  ┌────────────────┐  ┌──────────────┐ │  │
│  │  │VerbRepository│  │ArticleRepository│  │ClauseRepository│ │
│  │  └──────┬───────┘  └────────┬───────┘  └──────┬───────┘ │  │
│  └─────────┼──────────────────┬──────────────────┼──────────┘  │
│            │                  │                  │              │
│            └──────────────────┴──────────────────┘              │
└─────────────────────────────────┬───────────────────────────────┘
                                  │
                    ┌─────────────┴─────────────┐
                    │                           │
                    ▼                           ▼
┌─────────────────────────────────────────────────────────────────┐
│                           DATA LAYER                             │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌────────────────────────┐      ┌──────────────────────────┐  │
│  │    LOCAL (Room DB)     │      │   REMOTE (Firestore)     │  │
│  ├────────────────────────┤      ├──────────────────────────┤  │
│  │                        │      │                          │  │
│  │  ┌──────────────────┐ │      │  FirestoreRepository     │  │
│  │  │ KGemanDatabase   │ │      │  ┌────────────────────┐  │  │
│  │  │                  │ │      │  │  getVerbs()        │  │  │
│  │  │  ┌────────────┐  │ │      │  │  getArticles()     │  │  │
│  │  │  │    DAOs    │  │ │      │  │  getClauses()      │  │  │
│  │  │  ├────────────┤  │ │      │  └────────────────────┘  │  │
│  │  │  │VerbDao     │  │ │      │           │               │  │
│  │  │  │ArticleDao  │  │ │      │           │ Firestore SDK │  │
│  │  │  │ClauseDao   │  │ │      │           ▼               │  │
│  │  │  └────────────┘  │ │      │  ┌────────────────────┐  │  │
│  │  │                  │ │      │  │Collections:        │  │  │
│  │  │  ┌────────────┐  │ │      │  │ verbs_prepositions│  │  │
│  │  │  │  Entities  │  │ │      │  │ articles          │  │  │
│  │  │  ├────────────┤  │ │      │  │ clauses           │  │  │
│  │  │  │VerbEntity  │  │ │      │  └────────────────────┘  │  │
│  │  │  │ArticleEntity│ │      │                          │  │
│  │  │  │ClauseEntity│  │ │      │  Security: Read-only     │  │
│  │  │  └────────────┘  │ │      │  Auth: None (anonymous)  │  │
│  │  └──────────────────┘ │      └──────────────────────────┘  │
│  │                        │                                     │
│  │  Flow<List<T>>         │      suspend fun getX(): List<T>   │
│  └────────────────────────┘      └──────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                                  │
                                  │
                                  ▼
┌─────────────────────────────────────────────────────────────────┐
│                      SYNC & BACKGROUND TASKS                     │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                      SyncManager                          │  │
│  │  ┌────────────────────────────────────────────────────┐  │  │
│  │  │  syncFirestoreToRoom()                             │  │  │
│  │  │  - Fetch from Firestore                            │  │  │
│  │  │  - Save to Room                                     │  │  │
│  │  │  - Update last_sync timestamp                       │  │  │
│  │  └────────────────────────────────────────────────────┘  │  │
│  │                                                            │  │
│  │  ┌────────────────────────────────────────────────────┐  │  │
│  │  │  scheduleWeeklySync()                              │  │  │
│  │  │  - Uses WorkManager                                 │  │  │
│  │  │  - Runs SyncWorker every 7 days                     │  │  │
│  │  │  - Requires network connection                      │  │  │
│  │  └────────────────────────────────────────────────────┘  │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                      WorkManager                          │  │
│  │  ┌────────────────────────────────────────────────────┐  │  │
│  │  │  SyncWorker (CoroutineWorker)                      │  │  │
│  │  │  - Periodic work: 7 days                            │  │  │
│  │  │  - Constraints: Network required                    │  │  │
│  │  │  - Backoff: Exponential                             │  │  │
│  │  └────────────────────────────────────────────────────┘  │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

## Data Flow

### Initial App Launch
```
1. MainActivity.onCreate()
   ↓
2. SyncManager.needsInitialSync()
   ↓ (if true)
3. SyncManager.syncFirestoreToRoom()
   ↓
4. FirestoreRepository.getVerbs/Articles/Clauses()
   ↓
5. Transform Firestore docs → Domain models
   ↓
6. Convert Domain models → Room Entities
   ↓
7. Room DAO.insertAll(entities)
   ↓
8. Update SharedPreferences (last_sync_timestamp)
   ↓
9. SyncManager.scheduleWeeklySync()
```

### Loading Content in Fragment
```
1. Fragment.onViewCreated()
   ↓
2. ViewModel initialization
   ↓
3. Repository.getAll() → Flow<List<DomainModel>>
   ↓
4. Room DAO.getAll() → Flow<List<Entity>>
   ↓
5. Transform Entities → Domain Models
   ↓
6. Flow → LiveData (asLiveData())
   ↓
7. Fragment observes LiveData
   ↓
8. RecyclerView Adapter.submitList(data)
   ↓
9. UI displays data
```

### Weekly Background Sync
```
1. WorkManager triggers SyncWorker (every 7 days)
   ↓
2. Check network availability (constraint)
   ↓
3. SyncWorker.doWork()
   ↓
4. SyncManager.syncFirestoreToRoom()
   ↓
5. Fetch latest data from Firestore
   ↓
6. Update Room database
   ↓
7. Room notifies observers via Flow
   ↓
8. UI automatically updates (reactive)
```

## Technology Stack

### Core
- **Language**: Kotlin 1.9.20
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Build System**: Gradle 8.2 with Kotlin DSL

### Architecture
- **Pattern**: MVVM (Model-View-ViewModel)
- **Structure**: Clean Architecture (Data/Domain/Presentation)
- **Dependency Injection**: Manual (no Hilt/Dagger for simplicity)

### Data Layer
- **Remote**: Firebase Firestore (BOM 32.7.0)
- **Local**: Room Database 2.6.1
- **Sync**: WorkManager 2.9.0

### UI Layer
- **Design**: Material Design 3
- **Layouts**: XML with ViewBinding
- **Navigation**: Bottom Navigation View
- **Lists**: RecyclerView with DiffUtil

### Async
- **Coroutines**: kotlinx-coroutines-android 1.7.3
- **Flow**: For reactive data streams
- **LiveData**: For lifecycle-aware UI updates

## Key Design Decisions

### 1. Offline-First Architecture
**Why**: Ensures app works without internet connection
- All content cached in Room Database
- UI reads from local cache (fast)
- Background sync keeps data fresh

### 2. No User Authentication
**Why**: Simplifies Google Play approval
- No personal data collection
- Public read-only content
- Privacy-friendly

### 3. Weekly Sync Schedule
**Why**: Balance between freshness and battery/data usage
- Grammar content rarely changes
- WorkManager handles constraints
- Manual refresh option available

### 4. Clean Architecture Separation
**Why**: Maintainability and testability
- **Data Layer**: Database and network logic
- **Domain Layer**: Business models and repository contracts
- **Presentation Layer**: UI and user interaction

### 5. ViewBinding over DataBinding
**Why**: Simpler and faster
- Compile-time safety
- Less boilerplate than findViewById
- Better performance than DataBinding

## Security & Privacy

### Firestore Security Rules
```javascript
// Read-only public access
allow read: if true;
allow write: if false;  // Admin SDK only
```

### Android Permissions
- `INTERNET`: Required for Firestore
- `ACCESS_NETWORK_STATE`: For offline detection
- **No other permissions**: Privacy-compliant

### Data Collection
- **Personal Data**: None
- **Analytics**: None
- **Tracking**: None
- **User Accounts**: None

## Testing Strategy

### Unit Tests (Not implemented yet, but recommended)
- ViewModel logic
- Repository transformations
- Entity ↔ Domain model mappers

### Integration Tests
- Room database operations
- Firestore queries (with emulator)
- SyncManager flow

### UI Tests
- Fragment navigation
- RecyclerView display
- Offline mode indicator

## Performance Considerations

### Database
- Room uses SQLite (fast local queries)
- Indexed primary keys (id fields)
- Efficient DiffUtil for RecyclerView updates

### Network
- Firestore SDK caches automatically
- Single collection queries (no joins)
- Batch operations for sync

### Memory
- ViewBinding (no memory leaks)
- Fragment lifecycle awareness
- RecyclerView recycling

## Future Enhancements

### Short Term
- [ ] Search functionality
- [ ] Favorites/bookmarks
- [ ] Dark mode support

### Medium Term
- [ ] Audio pronunciation (TTS)
- [ ] Quiz mode
- [ ] Progress tracking

### Long Term
- [ ] User accounts (optional)
- [ ] Social features (share progress)
- [ ] Advanced grammar topics

## File Organization

```
k-geman/
├── app/
│   ├── src/main/
│   │   ├── java/com/kgeman/
│   │   │   ├── data/
│   │   │   │   ├── local/          # Room DB
│   │   │   │   │   ├── dao/
│   │   │   │   │   ├── entities/
│   │   │   │   │   └── KGemanDatabase.kt
│   │   │   │   ├── remote/         # Firestore
│   │   │   │   │   └── FirestoreRepository.kt
│   │   │   │   └── SyncManager.kt
│   │   │   ├── domain/
│   │   │   │   ├── models/         # Business models
│   │   │   │   └── repository/     # Repository interfaces
│   │   │   └── presentation/
│   │   │       ├── adapters/       # RecyclerView
│   │   │       ├── fragments/      # UI screens
│   │   │       ├── viewmodels/     # MVVM
│   │   │       └── MainActivity.kt
│   │   ├── res/
│   │   │   ├── layout/             # XML layouts
│   │   │   ├── values/             # Strings, colors, themes
│   │   │   ├── menu/               # Bottom navigation
│   │   │   └── xml/                # Backup rules
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── google-services.json
├── firestore.rules
├── sample-firestore-data.json
├── BUILD_INSTRUCTIONS.md
├── PRIVACY_POLICY.md
├── README.md
├── build.gradle.kts
└── settings.gradle.kts
```

## Build & Deployment

### Development
1. Clone repository
2. Replace `google-services.json` with real Firebase config
3. Sync Gradle
4. Build and run

### Release
1. Update version in `build.gradle.kts`
2. Build release APK/AAB
3. Sign with release keystore
4. Upload to Google Play Console

### Firestore Setup
1. Create Firebase project
2. Enable Firestore Database
3. Deploy security rules
4. Upload sample data
5. Verify collections exist

## Conclusion

This architecture provides:
- ✅ Clean separation of concerns
- ✅ Offline-first user experience
- ✅ Privacy-compliant design
- ✅ Beginner-friendly code structure
- ✅ Scalable for future features
- ✅ Google Play ready

The app is production-ready and follows Android best practices for 2024+.
