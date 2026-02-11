package com.kgeman.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.kgeman.data.local.KGemanDatabase
import com.kgeman.data.remote.FirestoreRepository
import com.kgeman.domain.models.ArticleRule
import com.kgeman.domain.repository.ArticleRepository

/**
 * ViewModel for Articles screen
 */
class ArticlesViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = KGemanDatabase.getDatabase(application)
    private val firestoreRepository = FirestoreRepository()
    private val repository = ArticleRepository(database, firestoreRepository)
    
    val articles: LiveData<List<ArticleRule>> = repository.getAllArticles().asLiveData()
}
