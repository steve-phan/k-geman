package com.example.kgeman

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnVerbs).setOnClickListener {
            startActivity(Intent(this, VerbsWithPrepositionsActivity::class.java))
        }

        findViewById<Button>(R.id.btnArticles).setOnClickListener {
            startActivity(Intent(this, ArticlesActivity::class.java))
        }

        findViewById<Button>(R.id.btnClauses).setOnClickListener {
            startActivity(Intent(this, ClausesActivity::class.java))
        }
    }
}
