package com.damarazka.quran.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices

class SharedViewModel(context: Context) : ViewModel() {
    private val fusedLocation = LocationServices.getFusedLocationProviderClient(context)
    private var _lastKnownLocation = MutableLiveData<List<Double>>()
    val lastKnownLocation get() = _lastKnownLocation as LiveData<List<Double>>


    @SuppressLint("MissingPermission")
    fun getKnownLastLocation() {
        fusedLocation.lastLocation.addOnSuccessListener {
            val listLatLong = listOf(it.latitude,it.longitude)
            _lastKnownLocation.postValue(listLatLong)
        }

        fusedLocation.lastLocation.addOnFailureListener{
            Log.e("SharedViewModel", "getKnownLastLocation: " + it.localizedMessage )
        }
    }
}