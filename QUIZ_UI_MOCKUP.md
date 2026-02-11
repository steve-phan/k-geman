# Quiz UI Mockup

```
┌─────────────────────────────────────────────────┐
│  K-Geman                        ⏱️ 01:23        │
├─────────────────────────────────────────────────┤
│                                                 │
│  Frage 5 von 10                                 │
│  ████████████████░░░░░░░ 50%                    │
│                                                 │
│  ┌────────────────────────────────────────┐    │
│  │  Welche Präposition passt zu 'denken'? │    │
│  │                                          │    │
│  │  [ Hinweis anzeigen ]                    │    │
│  │                                          │    │
│  │  ┌────────────────────────────────┐    │    │
│  │  │ 💡 Kasus: Akkusativ            │    │    │
│  │  └────────────────────────────────┘    │    │
│  └────────────────────────────────────────┘    │
│                                                 │
│  ○ an                                           │
│  ● auf                                          │
│  ○ mit                                          │
│  ○ von                                          │
│                                                 │
│  ┌────────────────────────────────────────┐    │
│  │ ✓ Richtig!                              │    │
│  │                                          │    │
│  │ Ich denke an dich.                       │    │
│  └────────────────────────────────────────┘    │
│                                                 │
│  [ Zurück ]  [ Wiederholen ]  [ Weiter ]       │
│                                                 │
├─────────────────────────────────────────────────┤
│   🔤 Verben   📋 Artikel   📝 Sätze   ❓ Quiz  │
└─────────────────────────────────────────────────┘
```

## UI Elements Breakdown

### Header Section
- **Timer Card (Top Right)**: 
  - Clock icon + elapsed time in MM:SS format
  - Updates every second
  - Blue primary color theme

### Progress Section
- **Progress Text**: "Frage X von Y"
- **Progress Bar**: Visual indicator (0-100%)
  - Fills with primary blue color
  - Shows completion percentage

### Question Card
- **Question Text**: 
  - Large, bold font
  - Dark text on white card
  - Material Design elevation
- **Hint Button**:
  - Text button style
  - Primary color when inactive
  - Toggles hint visibility
- **Hint Card** (when shown):
  - Light blue background
  - 💡 emoji prefix
  - Contextual help text

### Answer Options
- **Radio Buttons**:
  - 4 Material Design radio buttons
  - Large tap targets
  - Clear text labels
  - Correct answer highlighted in blue after selection

### Feedback Card (after answering)
- **Success State**:
  - ✓ Richtig! in green
  - Light green background
  - Example sentence shown
- **Error State**:
  - ✗ Falsch! in red
  - Light red background
  - Shows correct answer
  - Example sentence shown

### Navigation Buttons
- **Zurück (Back)**:
  - Outlined button
  - Disabled on first question (gray)
- **Wiederholen (Repeat)**:
  - Outlined button
  - Always enabled
  - Resets current question
- **Weiter (Next)**:
  - Filled button with primary color
  - Disabled on last question

### Bottom Navigation
- 4 tabs with icons:
  - Verben (Verbs) - Edit icon
  - Artikel (Articles) - Info icon
  - Sätze (Clauses) - Sort icon
  - Quiz - Help icon (highlighted when active)

## Color Scheme

- **Primary Blue**: #0062A3
- **Primary Container**: #D0E4FF (light blue for hints)
- **Success Green**: Material Green 600
- **Error Red**: #D32F2F
- **Surface White**: #FDFBFF
- **On Surface Dark**: #1A1C1E

## Interactions

1. **Tap Answer**: Select an option, triggers validation
2. **Tap Hint**: Toggle hint visibility
3. **Tap Zurück**: Go to previous question (if not first)
4. **Tap Weiter**: Go to next question (if not last)
5. **Tap Wiederholen**: Reset current question state
6. **Timer**: Auto-updates every second

## Responsive Behavior

- All elements scale appropriately
- Touch targets meet Material Design guidelines (48dp minimum)
- Scrollable if content exceeds screen height
- Landscape orientation supported
