package com.example.kgeman.data

import com.example.kgeman.model.GrammaticalCase
import com.example.kgeman.model.VerbWithPreposition

object VerbsData {
    fun getVerbsWithPrepositions(): List<VerbWithPreposition> {
        return listOf(
            VerbWithPreposition(
                verb = "denken",
                preposition = "an",
                case = GrammaticalCase.ACCUSATIVE,
                example = "Ich denke an dich.",
                translation = "I think of you."
            ),
            VerbWithPreposition(
                verb = "warten",
                preposition = "auf",
                case = GrammaticalCase.ACCUSATIVE,
                example = "Ich warte auf den Bus.",
                translation = "I'm waiting for the bus."
            ),
            VerbWithPreposition(
                verb = "sich interessieren",
                preposition = "für",
                case = GrammaticalCase.ACCUSATIVE,
                example = "Er interessiert sich für Musik.",
                translation = "He is interested in music."
            ),
            VerbWithPreposition(
                verb = "sprechen",
                preposition = "über",
                case = GrammaticalCase.ACCUSATIVE,
                example = "Wir sprechen über das Wetter.",
                translation = "We're talking about the weather."
            ),
            VerbWithPreposition(
                verb = "sich freuen",
                preposition = "auf",
                case = GrammaticalCase.ACCUSATIVE,
                example = "Ich freue mich auf die Ferien.",
                translation = "I'm looking forward to the holidays."
            ),
            VerbWithPreposition(
                verb = "teilnehmen",
                preposition = "an",
                case = GrammaticalCase.DATIVE,
                example = "Sie nimmt an dem Kurs teil.",
                translation = "She participates in the course."
            ),
            VerbWithPreposition(
                verb = "träumen",
                preposition = "von",
                case = GrammaticalCase.DATIVE,
                example = "Er träumt von einem neuen Auto.",
                translation = "He dreams of a new car."
            ),
            VerbWithPreposition(
                verb = "sich beschäftigen",
                preposition = "mit",
                case = GrammaticalCase.DATIVE,
                example = "Ich beschäftige mich mit Deutsch.",
                translation = "I'm studying German."
            ),
            VerbWithPreposition(
                verb = "sich erkundigen",
                preposition = "nach",
                case = GrammaticalCase.DATIVE,
                example = "Er erkundigt sich nach dem Weg.",
                translation = "He asks for directions."
            ),
            VerbWithPreposition(
                verb = "abhängen",
                preposition = "von",
                case = GrammaticalCase.DATIVE,
                example = "Das hängt von dir ab.",
                translation = "That depends on you."
            )
        )
    }
}
