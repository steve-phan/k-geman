package com.example.kgeman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kgeman.adapter.VerbsAdapter
import com.example.kgeman.data.VerbsData

class VerbsWithPrepositionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verbs)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.verbsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        val verbs = VerbsData.getVerbsWithPrepositions()
        recyclerView.adapter = VerbsAdapter(verbs)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
