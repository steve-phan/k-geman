package com.example.kgeman.data

import com.example.kgeman.model.ClauseExample
import com.example.kgeman.model.ClauseType

object ClausesData {
    fun getClauseExamples(): List<ClauseExample> {
        return listOf(
            ClauseExample(
                type = ClauseType.SUBORDINATE,
                conjunction = "weil",
                mainClause = "Ich lerne Deutsch",
                subordinateClause = "weil ich nach Deutschland reisen möchte",
                fullSentence = "Ich lerne Deutsch, weil ich nach Deutschland reisen möchte.",
                translation = "I'm learning German because I want to travel to Germany.",
                explanation = "The verb 'möchte' moves to the end in the subordinate clause."
            ),
            ClauseExample(
                type = ClauseType.SUBORDINATE,
                conjunction = "dass",
                mainClause = "Ich denke",
                subordinateClause = "dass Deutsch eine schöne Sprache ist",
                fullSentence = "Ich denke, dass Deutsch eine schöne Sprache ist.",
                translation = "I think that German is a beautiful language.",
                explanation = "The verb 'ist' moves to the end after 'dass'."
            ),
            ClauseExample(
                type = ClauseType.SUBORDINATE,
                conjunction = "wenn",
                mainClause = "Ich bin glücklich",
                subordinateClause = "wenn ich Deutsch spreche",
                fullSentence = "Ich bin glücklich, wenn ich Deutsch spreche.",
                translation = "I'm happy when I speak German.",
                explanation = "'wenn' is a subordinating conjunction that sends the verb to the end."
            ),
            ClauseExample(
                type = ClauseType.SUBORDINATE,
                conjunction = "obwohl",
                mainClause = "Ich lerne weiter",
                subordinateClause = "obwohl es schwierig ist",
                fullSentence = "Ich lerne weiter, obwohl es schwierig ist.",
                translation = "I continue learning, although it's difficult.",
                explanation = "The verb 'ist' goes to the end in the subordinate clause with 'obwohl'."
            ),
            ClauseExample(
                type = ClauseType.SUBORDINATE,
                conjunction = "während",
                mainClause = "Ich höre Musik",
                subordinateClause = "während ich arbeite",
                fullSentence = "Ich höre Musik, während ich arbeite.",
                translation = "I listen to music while I work.",
                explanation = "'während' sends the verb 'arbeite' to the end."
            ),
            ClauseExample(
                type = ClauseType.COMPOUND,
                conjunction = "und",
                mainClause = "Ich lerne Deutsch",
                subordinateClause = "und ich übe jeden Tag",
                fullSentence = "Ich lerne Deutsch und ich übe jeden Tag.",
                translation = "I learn German and I practice every day.",
                explanation = "'und' is a coordinating conjunction - word order doesn't change."
            ),
            ClauseExample(
                type = ClauseType.COMPOUND,
                conjunction = "aber",
                mainClause = "Deutsch ist schwer",
                subordinateClause = "aber es macht Spaß",
                fullSentence = "Deutsch ist schwer, aber es macht Spaß.",
                translation = "German is difficult, but it's fun.",
                explanation = "'aber' is coordinating - normal word order in both clauses."
            ),
            ClauseExample(
                type = ClauseType.COMPOUND,
                conjunction = "oder",
                mainClause = "Möchtest du Kaffee",
                subordinateClause = "oder möchtest du Tee",
                fullSentence = "Möchtest du Kaffee oder möchtest du Tee?",
                translation = "Would you like coffee or would you like tea?",
                explanation = "'oder' is coordinating - verb position stays the same."
            ),
            ClauseExample(
                type = ClauseType.SUBORDINATE,
                conjunction = "als",
                mainClause = "Ich war glücklich",
                subordinateClause = "als ich in Berlin war",
                fullSentence = "Ich war glücklich, als ich in Berlin war.",
                translation = "I was happy when I was in Berlin.",
                explanation = "'als' for past events sends the verb to the end."
            ),
            ClauseExample(
                type = ClauseType.SUBORDINATE,
                conjunction = "bevor",
                mainClause = "Ich dusche mich",
                subordinateClause = "bevor ich frühstücke",
                fullSentence = "Ich dusche mich, bevor ich frühstücke.",
                translation = "I shower before I have breakfast.",
                explanation = "'bevor' sends the verb 'frühstücke' to the end."
            )
        )
    }
}
