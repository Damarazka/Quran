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
    @SuppressLint("MissingPermission")
    fun getKnownLastLocation() : LiveData<List<String>> {
        val lastKnownLocation = MutableLiveData<List<String>>()
        fusedLocation.lastLocation.addOnSuccessListener {
            if (it != null) {
                val geocoder = Geocoder(context, Locale.getDefault())
                geocoder.getFromLocation(
                    -7.8193722,
                    112.0415361,
                    //it.,
                    //it.longitude,
                    1
                ) { listAddress ->
                    val city = listAddress[0].subAdminArea
                    val resultOfCity = city.split(" ")
                    val subLocality = listAddress[0].subLocality
                    val locality = listAddress[0].locality
                    val resultLocation = "$subLocality, $locality"
                    Log.i("LocPref", "getKnownLastLocation: $resultOfCity")

                    val listCity = listOf(resultOfCity[0], resultLocation)
                    lastKnownLocation.postValue(listCity)
                }
            } else {
                Toast.makeText(context, "Sorry something wrong", Toast.LENGTH_SHORT).show()
            }
        }

        fusedLocation.lastLocation.addOnFailureListener{
            Log.e("SharedViewModel", "getKnownLastLocation: " + it.localizedMessage )
        }
        return lastKnownLocation
    }
}