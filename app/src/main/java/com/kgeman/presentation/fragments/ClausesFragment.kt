package com.kgeman.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kgeman.databinding.FragmentClausesBinding
import com.kgeman.presentation.adapters.ClausesAdapter
import com.kgeman.presentation.viewmodels.ClausesViewModel

/**
 * Fragment displaying list of clause examples
 */
class ClausesFragment : Fragment() {
    
    private var _binding: FragmentClausesBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ClausesViewModel by viewModels()
    private val adapter = ClausesAdapter()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClausesBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        observeData()
    }
    
    private fun setupRecyclerView() {
        binding.clausesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ClausesFragment.adapter
        }
    }
    
    private fun observeData() {
        viewModel.clauses.observe(viewLifecycleOwner) { clauses ->
            if (clauses.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.clausesRecyclerView.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.clausesRecyclerView.visibility = View.VISIBLE
                adapter.submitList(clauses)
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
