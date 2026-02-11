package com.kgeman.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kgeman.databinding.ItemArticleBinding
import com.kgeman.domain.models.ArticleRule

/**
 * Adapter for displaying article rules in RecyclerView
 */
class ArticlesAdapter : ListAdapter<ArticleRule, ArticlesAdapter.ArticleViewHolder>(ArticleDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class ArticleViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleRule) {
            binding.articleText.text = article.article
            binding.nounText.text = article.noun
            binding.genderText.text = article.gender
            binding.ruleHintText.text = article.ruleHint
        }
    }
    
    private class ArticleDiffCallback : DiffUtil.ItemCallback<ArticleRule>() {
        override fun areItemsTheSame(oldItem: ArticleRule, newItem: ArticleRule): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: ArticleRule, newItem: ArticleRule): Boolean {
            return oldItem == newItem
        }
    }
}
