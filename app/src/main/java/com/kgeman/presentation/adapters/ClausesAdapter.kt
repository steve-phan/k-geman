package com.kgeman.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kgeman.databinding.ItemClauseBinding
import com.kgeman.domain.models.ClauseExample

/**
 * Adapter for displaying clause examples in RecyclerView
 */
class ClausesAdapter : ListAdapter<ClauseExample, ClausesAdapter.ClauseViewHolder>(ClauseDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClauseViewHolder {
        val binding = ItemClauseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClauseViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ClauseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class ClauseViewHolder(private val binding: ItemClauseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clause: ClauseExample) {
            binding.conjunctionText.text = clause.conjunction
            binding.mainClauseText.text = clause.mainClause
            binding.subClauseText.text = clause.subordinateClause
            binding.explanationText.text = clause.ruleExplanation
        }
    }
    
    private class ClauseDiffCallback : DiffUtil.ItemCallback<ClauseExample>() {
        override fun areItemsTheSame(oldItem: ClauseExample, newItem: ClauseExample): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: ClauseExample, newItem: ClauseExample): Boolean {
            return oldItem == newItem
        }
    }
}
