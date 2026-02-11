package com.kgeman.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.kgeman.data.SyncManager
import com.kgeman.data.local.KGemanDatabase
import com.kgeman.data.remote.FirestoreRepository
import com.kgeman.domain.models.VerbWithPreposition
import com.kgeman.domain.repository.VerbRepository

/**
 * ViewModel for Verbs screen
 */
class VerbsViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = KGemanDatabase.getDatabase(application)
    private val firestoreRepository = FirestoreRepository()
    private val repository = VerbRepository(database, firestoreRepository)
    
    val verbs: LiveData<List<VerbWithPreposition>> = repository.getAllVerbs().asLiveData()
}
