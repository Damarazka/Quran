package com.damarazka.quran.presentation.adzan

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.damarazka.quran.R
import com.damarazka.quran.core.data.Resource
import com.damarazka.quran.databinding.FragmentAdzanBinding
import com.damarazka.quran.presentation.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class AdzanFragment : Fragment() {
    private var _binding: FragmentAdzanBinding? = null
    private val binding get() = _binding as FragmentAdzanBinding
    private val adzanViewModel: AdzanViewModel by viewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdzanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adzanViewModel.getDailyAdzanTime().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    binding.tvLocation.text = it.data?.listAddress?.get(1)
                    when (val listCity = it.data?.listCity) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            val idCity = listCity.data?.get(0)?.idCity
                            val city = listCity.data?.get(0)?.location
                            Log.i("AdzanFragment", "idcity of $city: $idCity")
                            Toast.makeText(
                                context,
                                "id city of $city : $idCity",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Error -> {}
                        else -> {
                            Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is Resource.Error->{}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
