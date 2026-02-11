package com.kgeman.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kgeman.databinding.FragmentArticlesBinding
import com.kgeman.presentation.adapters.ArticlesAdapter
import com.kgeman.presentation.viewmodels.ArticlesViewModel

/**
 * Fragment displaying list of article rules
 */
class ArticlesFragment : Fragment() {
    
    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ArticlesViewModel by viewModels()
    private val adapter = ArticlesAdapter()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        observeData()
    }
    
    private fun setupRecyclerView() {
        binding.articlesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ArticlesFragment.adapter
        }
    }
    
    private fun observeData() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            if (articles.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.articlesRecyclerView.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.articlesRecyclerView.visibility = View.VISIBLE
                adapter.submitList(articles)
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
