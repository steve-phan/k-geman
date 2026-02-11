package com.example.kgeman.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kgeman.R
import com.example.kgeman.model.ArticleNoun

class ArticlesAdapter(private val nouns: List<ArticleNoun>) : 
    RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val articleText: TextView = view.findViewById(R.id.articleText)
        val nounText: TextView = view.findViewById(R.id.nounText)
        val pluralText: TextView = view.findViewById(R.id.pluralText)
        val translationText: TextView = view.findViewById(R.id.translationText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val noun = nouns[position]
        holder.articleText.text = noun.article.name.lowercase()
        holder.nounText.text = noun.noun
        holder.pluralText.text = "Plural: die ${noun.plural}"
        holder.translationText.text = noun.translation
        
        // Color code by article
        val color = when(noun.article) {
            com.example.kgeman.model.Article.DER -> 0xFF4A90E2.toInt()  // Blue
            com.example.kgeman.model.Article.DIE -> 0xFFE24A90.toInt()  // Pink
            com.example.kgeman.model.Article.DAS -> 0xFF4AE290.toInt()  // Green
        }
        holder.articleText.setTextColor(color)
    }

    override fun getItemCount() = nouns.size
}
