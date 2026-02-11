package com.example.kgeman.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kgeman.R
import com.example.kgeman.model.ClauseExample

class ClausesAdapter(private val clauses: List<ClauseExample>) : 
    RecyclerView.Adapter<ClausesAdapter.ClauseViewHolder>() {

    class ClauseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val typeText: TextView = view.findViewById(R.id.typeText)
        val conjunctionText: TextView = view.findViewById(R.id.conjunctionText)
        val sentenceText: TextView = view.findViewById(R.id.sentenceText)
        val translationText: TextView = view.findViewById(R.id.translationText)
        val explanationText: TextView = view.findViewById(R.id.explanationText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClauseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clause, parent, false)
        return ClauseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClauseViewHolder, position: Int) {
        val clause = clauses[position]
        holder.typeText.text = when(clause.type) {
            com.example.kgeman.model.ClauseType.MAIN -> "Hauptsatz"
            com.example.kgeman.model.ClauseType.SUBORDINATE -> "Nebensatz"
            com.example.kgeman.model.ClauseType.COMPOUND -> "Zusammengesetzter Satz"
        }
        holder.conjunctionText.text = "Conjunction: ${clause.conjunction}"
        holder.sentenceText.text = clause.fullSentence
        holder.translationText.text = clause.translation
        holder.explanationText.text = clause.explanation
    }

    override fun getItemCount() = clauses.size
}
