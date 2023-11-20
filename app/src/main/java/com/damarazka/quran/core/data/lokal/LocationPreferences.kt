package com.damarazka.quran.core.data.lokal

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import java.util.Locale

class LocationPreferences(val context: Context) {
    private val fusedLocation = LocationServices.getFusedLocationProviderClient(context)
    private var _lastKnownLocation = MutableLiveData<List<String>>()
    val lastKnownLocation get() = _lastKnownLocation as LiveData<List<String>>

    @SuppressLint("MissingPermission")
    fun getKnownLastLocation() {
        fusedLocation.lastLocation.addOnSuccessListener {
            if (it != null) {
                val geocoder = Geocoder(context, Locale.getDefault())
                geocoder.getFromLocation(
                    it.latitude,
                    it.longitude,
                    1
                ) { listAddress ->
                    val city = listAddress[0].subAdminArea
                    val resultOfCity = city.split(" ")
                    val subLocality = listAddress[0].subLocality
                    val locality = listAddress[0].locality
                    val resultLocation = "$subLocality, $locality"

                    val listCity = listOf(resultOfCity[0], resultLocation)
                    _lastKnownLocation.postValue(listCity)
                }
            } else {
                Toast.makeText(context, "Sorry something wrong", Toast.LENGTH_SHORT).show()
            }
        }

        fusedLocation.lastLocation.addOnFailureListener{
            Log.e("SharedViewModel", "getKnownLastLocation: " + it.localizedMessage )
        }
    }
}