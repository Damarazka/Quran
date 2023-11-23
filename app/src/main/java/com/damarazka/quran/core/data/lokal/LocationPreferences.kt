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
    fun getKnownLastLocation(): LiveData<List<String>> {
        val lastKnownLocation = MutableLiveData<List<String>>()
        fusedLocation.lastLocation.addOnSuccessListener {
            if (it != null) {
                val geocoder = Geocoder(context, Locale.getDefault())
                geocoder.getFromLocation(
                    it.latitude,
                    it.longitude,
                    1
                ) { listAddress ->
                    val city = listAddress[0].subAdminArea
                    val arrayCity = city.split(" ")
                    val subLocality = listAddress[0].subLocality
                    val locality = listAddress[0].locality
                    val resultLocation = "$subLocality, $locality"

                    val currentLanguage = Locale.getDefault().language
                    Log.i("LocPref", "getKnownLastLocation: $currentLanguage")

                    val cityResult:String = when (currentLanguage) {
                        "in" -> {
                            var result = ""
                            for (i in 1 until arrayCity.size){
                                result += arrayCity[i] + ""
                            }
                            result
                        }
                        "en" -> {
                            var result = ""
                            for (i in 0 until arrayCity.size -1){
                                result += arrayCity[i] + ""
                            }
                            result
                        }else ->{
                            Log.e("LocPref", "error: current language not undefined, " )
                            "Jakarta"
                        }
                    }
                    val listCity = listOf(cityResult, resultLocation)
                    Log.e("LogPref", "data : $listCity ", )
                    lastKnownLocation.postValue(listCity)
                }
            } else {
                Toast.makeText(context, "Sorry something wrong", Toast.LENGTH_SHORT).show()
            }
        }

        fusedLocation.lastLocation.addOnFailureListener {
            Log.e("SharedViewModel", "getKnownLastLocation: " + it.localizedMessage)
        }
        return lastKnownLocation
    }
}