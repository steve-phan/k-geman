# K-Geman Android App Structure

## Overview
A Kotlin-based Android application for learning German grammar, focusing on three key areas:
1. Verben mit Präpositionen (Verbs with Prepositions)
2. Artikel (Articles: der/die/das)
3. Haupt- und Nebensätze (Main and Subordinate Clauses)

## Project Statistics
- **Total Kotlin Code**: 603 lines
- **Number of Activities**: 4
- **Number of Adapters**: 3
- **Number of Data Models**: 3
- **Layout Files**: 7
- **Sample Data Examples**: 35 total (10 verbs, 15 articles, 10 clauses)

## File Structure

```
k-geman/
├── app/
│   ├── build.gradle.kts          # App-level build configuration
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml   # App manifest with 4 activities
│       ├── java/com/example/kgeman/
│       │   ├── MainActivity.kt                    # Main menu screen
│       │   ├── VerbsWithPrepositionsActivity.kt   # Verbs learning screen
│       │   ├── ArticlesActivity.kt                # Articles practice screen
│       │   ├── ClausesActivity.kt                 # Clauses learning screen
│       │   ├── model/
│       │   │   ├── VerbWithPreposition.kt         # Verb data model
│       │   │   ├── ArticleNoun.kt                 # Article/noun data model
│       │   │   └── ClauseExample.kt               # Clause data model
│       │   ├── data/
│       │   │   ├── VerbsData.kt                   # 10 verb examples
│       │   │   ├── ArticlesData.kt                # 15 noun examples
│       │   │   └── ClausesData.kt                 # 10 clause examples
│       │   └── adapter/
│       │       ├── VerbsAdapter.kt                # RecyclerView adapter
│       │       ├── ArticlesAdapter.kt             # RecyclerView adapter
│       │       └── ClausesAdapter.kt              # RecyclerView adapter
│       └── res/
│           ├── layout/
│           │   ├── activity_main.xml              # Main menu layout
│           │   ├── activity_verbs.xml             # Verbs screen layout
│           │   ├── activity_articles.xml          # Articles screen layout
│           │   ├── activity_clauses.xml           # Clauses screen layout
│           │   ├── item_verb.xml                  # Verb card layout
│           │   ├── item_article.xml               # Article card layout
│           │   └── item_clause.xml                # Clause card layout
│           ├── values/
│           │   ├── strings.xml                    # App strings
│           │   ├── colors.xml                     # Color definitions
│           │   └── themes.xml                     # App theme
│           ├── drawable/
│           │   └── ic_launcher_foreground.xml     # App icon
│           └── mipmap-anydpi-v26/
│               ├── ic_launcher.xml
│               └── ic_launcher_round.xml
├── build.gradle.kts              # Project-level build configuration
├── settings.gradle.kts           # Gradle settings
├── gradle.properties             # Gradle properties
├── gradlew                       # Gradle wrapper script
└── README.md                     # Project documentation
```

## Features Detail

### 1. Verben mit Präpositionen
**Data Model**: VerbWithPreposition
- verb: String
- preposition: String
- case: GrammaticalCase (ACCUSATIVE, DATIVE, GENITIVE)
- example: String
- translation: String

**Examples Include**:
- denken + an (Akkusativ)
- warten + auf (Akkusativ)
- teilnehmen + an (Dativ)
- träumen + von (Dativ)
- And 6 more...

### 2. Artikel (der/die/das)
**Data Model**: ArticleNoun
- noun: String
- article: Article (DER, DIE, DAS)
- plural: String
- translation: String
- category: NounCategory (PERSON, ANIMAL, THING, PLACE, ABSTRACT)

**Examples Include**:
- der Mann, der Tisch, der Hund (masculine)
- die Frau, die Katze, die Blume (feminine)
- das Kind, das Haus, das Buch (neuter)

**UI Feature**: Color-coded articles
- DER (masculine) - Blue (#4A90E2)
- DIE (feminine) - Pink (#E24A90)
- DAS (neuter) - Green (#4AE290)

### 3. Haupt- und Nebensätze
**Data Model**: ClauseExample
- type: ClauseType (MAIN, SUBORDINATE, COMPOUND)
- conjunction: String
- mainClause: String
- subordinateClause: String
- fullSentence: String
- translation: String
- explanation: String

**Examples Include**:
- Subordinating conjunctions: weil, dass, wenn, obwohl, während, als, bevor
- Coordinating conjunctions: und, aber, oder

## UI Components

### MainActivity
Three buttons for navigation:
- "Verben mit Präpositionen"
- "Artikel (der/die/das)"
- "Haupt- und Nebensätze"

### Feature Screens
Each feature screen uses:
- RecyclerView with LinearLayoutManager
- CardView items with Material Design
- Custom adapters for data display
- Back navigation support

## Technical Details

### Dependencies
- AndroidX Core KTX 1.12.0
- AppCompat 1.6.1
- Material Components 1.10.0
- ConstraintLayout 2.1.4
- RecyclerView 1.3.2
- CardView 1.0.0

### Build Configuration
- minSdk: 24 (Android 7.0)
- targetSdk: 34 (Android 14)
- compileSdk: 34
- Kotlin 1.7.20
- Android Gradle Plugin 7.3.1
- ViewBinding enabled

## How the App Works

1. **Launch**: User sees MainActivity with three option buttons
2. **Select Feature**: User taps on one of the three learning areas
3. **View Content**: Selected activity displays a scrollable list of examples using RecyclerView
4. **Learn**: Each item shows German text, translation, and relevant grammatical information
5. **Navigate Back**: User can return to main menu via back button or action bar
