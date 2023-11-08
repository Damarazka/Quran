package com.damarazka.quran.presentation.Quran

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.damarazka.quran.adapter.QuranAdapter
import com.damarazka.quran.core.data.Resource
import com.damarazka.quran.databinding.FragmentQuranBinding
import com.damarazka.quran.presentation.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class QuranFragment : Fragment() {
    private var _binding : FragmentQuranBinding? = null
    private val binding get() = _binding as FragmentQuranBinding
    private val quranViewModel: QuranViewModel by viewModels { ViewModelFactory() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuranBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quranViewModel.getListSurah().observe(viewLifecycleOwner){
            when (it){
               is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    binding.rvQuran.apply {
                        val mAdapter = QuranAdapter()
                        mAdapter.setData(it.data)
                        adapter = mAdapter
                        layoutManager = LinearLayoutManager(context)
                    }
                    showLoading(false)
                }
               is  Resource.Error -> {
                   Snackbar.make(binding.root, "Error: " + it.message, Snackbar.LENGTH_INDEFINITE).show()
                   showLoading(false)
               }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if(isLoading){
                progressBar.visibility = View.VISIBLE
                rvQuran.visibility = View.INVISIBLE
            }else{
                progressBar.visibility = View.GONE
                rvQuran.visibility = View.VISIBLE
            }
        }
    }
}