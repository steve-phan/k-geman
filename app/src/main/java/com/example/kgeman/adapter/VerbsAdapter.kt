package com.example.kgeman.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kgeman.R
import com.example.kgeman.model.VerbWithPreposition

class VerbsAdapter(private val verbs: List<VerbWithPreposition>) : 
    RecyclerView.Adapter<VerbsAdapter.VerbViewHolder>() {

    class VerbViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val verbText: TextView = view.findViewById(R.id.verbText)
        val prepositionText: TextView = view.findViewById(R.id.prepositionText)
        val caseText: TextView = view.findViewById(R.id.caseText)
        val exampleText: TextView = view.findViewById(R.id.exampleText)
        val translationText: TextView = view.findViewById(R.id.translationText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerbViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_verb, parent, false)
        return VerbViewHolder(view)
    }

    override fun onBindViewHolder(holder: VerbViewHolder, position: Int) {
        val verb = verbs[position]
        holder.verbText.text = verb.verb
        holder.prepositionText.text = "+ ${verb.preposition}"
        holder.caseText.text = when(verb.case) {
            com.example.kgeman.model.GrammaticalCase.ACCUSATIVE -> "(Akkusativ)"
            com.example.kgeman.model.GrammaticalCase.DATIVE -> "(Dativ)"
            com.example.kgeman.model.GrammaticalCase.GENITIVE -> "(Genitiv)"
        }
        holder.exampleText.text = verb.example
        holder.translationText.text = verb.translation
    }

    override fun getItemCount() = verbs.size
}
