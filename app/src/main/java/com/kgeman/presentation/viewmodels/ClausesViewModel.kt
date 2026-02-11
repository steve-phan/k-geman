package com.kgeman.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.kgeman.data.local.KGemanDatabase
import com.kgeman.data.remote.FirestoreRepository
import com.kgeman.domain.models.ClauseExample
import com.kgeman.domain.repository.ClauseRepository

/**
 * ViewModel for Clauses screen
 */
class ClausesViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = KGemanDatabase.getDatabase(application)
    private val firestoreRepository = FirestoreRepository()
    private val repository = ClauseRepository(database, firestoreRepository)
    
    val clauses: LiveData<List<ClauseExample>> = repository.getAllClauses().asLiveData()
}
