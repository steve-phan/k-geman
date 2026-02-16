package com.kgeman.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kgeman.R
import com.kgeman.data.SyncManager
import com.kgeman.databinding.ActivityMainBinding
import com.kgeman.presentation.fragments.ArticlesFragment
import com.kgeman.presentation.fragments.ClausesFragment
import com.kgeman.presentation.fragments.QuizFragment
import com.kgeman.presentation.fragments.VerbsFragment
import kotlinx.coroutines.launch

/**
 * Main activity with bottom navigation
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var syncManager: SyncManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        syncManager = SyncManager(this)
        
        setupBottomNavigation()
        setupNetworkMonitoring()
        performInitialSync()
        
        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(VerbsFragment())
        }
    }
    
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_verbs -> {
                    loadFragment(VerbsFragment())
                    true
                }
                R.id.navigation_articles -> {
                    loadFragment(ArticlesFragment())
                    true
                }
                R.id.navigation_clauses -> {
                    loadFragment(ClausesFragment())
                    true
                }
                R.id.navigation_quiz -> {
                    loadFragment(QuizFragment())
                    true
                }
                else -> false
            }
        }
    }
    
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
    
    private fun setupNetworkMonitoring() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                runOnUiThread {
                    binding.offlineIndicator.visibility = View.GONE
                }
            }
            
            override fun onLost(network: Network) {
                runOnUiThread {
                    binding.offlineIndicator.visibility = View.VISIBLE
                }
            }
        })
        
        // Check initial network state
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        binding.offlineIndicator.visibility = if (isConnected) View.GONE else View.VISIBLE
    }
    
    private fun performInitialSync() {
        lifecycleScope.launch {
            try {
                // Check if initial sync is needed
                if (syncManager.needsInitialSync()) {
                    syncManager.syncFirestoreToRoom()
                }
                
                // Schedule weekly sync
                syncManager.scheduleWeeklySync()
            } catch (e: Exception) {
                // Handle sync error silently - app will work with cached data
            }
        }
    }
}
