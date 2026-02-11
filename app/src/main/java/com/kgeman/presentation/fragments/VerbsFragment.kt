package com.kgeman.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kgeman.databinding.FragmentVerbsBinding
import com.kgeman.presentation.adapters.VerbsAdapter
import com.kgeman.presentation.viewmodels.VerbsViewModel

/**
 * Fragment displaying list of verbs with prepositions
 */
class VerbsFragment : Fragment() {
    
    private var _binding: FragmentVerbsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: VerbsViewModel by viewModels()
    private val adapter = VerbsAdapter()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerbsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        observeData()
    }
    
    private fun setupRecyclerView() {
        binding.verbsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@VerbsFragment.adapter
        }
    }
    
    private fun observeData() {
        viewModel.verbs.observe(viewLifecycleOwner) { verbs ->
            if (verbs.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.verbsRecyclerView.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.verbsRecyclerView.visibility = View.VISIBLE
                adapter.submitList(verbs)
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
