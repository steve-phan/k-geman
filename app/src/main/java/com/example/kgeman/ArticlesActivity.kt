package com.example.kgeman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kgeman.adapter.ArticlesAdapter
import com.example.kgeman.data.ArticlesData

class ArticlesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.articlesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        val nouns = ArticlesData.getArticleNouns()
        recyclerView.adapter = ArticlesAdapter(nouns)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
