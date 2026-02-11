package com.example.kgeman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kgeman.adapter.ClausesAdapter
import com.example.kgeman.data.ClausesData

class ClausesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clauses)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.clausesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        val clauses = ClausesData.getClauseExamples()
        recyclerView.adapter = ClausesAdapter(clauses)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
