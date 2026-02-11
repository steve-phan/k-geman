package com.kgeman.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kgeman.databinding.ItemVerbBinding
import com.kgeman.domain.models.VerbWithPreposition

/**
 * Adapter for displaying verbs with prepositions in RecyclerView
 */
class VerbsAdapter : ListAdapter<VerbWithPreposition, VerbsAdapter.VerbViewHolder>(VerbDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerbViewHolder {
        val binding = ItemVerbBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VerbViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: VerbViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class VerbViewHolder(private val binding: ItemVerbBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(verb: VerbWithPreposition) {
            binding.verbText.text = verb.verb
            binding.prepositionText.text = "mit ${verb.preposition}"
            binding.caseText.text = verb.case
            binding.exampleText.text = verb.exampleSentence
        }
    }
    
    private class VerbDiffCallback : DiffUtil.ItemCallback<VerbWithPreposition>() {
        override fun areItemsTheSame(oldItem: VerbWithPreposition, newItem: VerbWithPreposition): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: VerbWithPreposition, newItem: VerbWithPreposition): Boolean {
            return oldItem == newItem
        }
    }
}
