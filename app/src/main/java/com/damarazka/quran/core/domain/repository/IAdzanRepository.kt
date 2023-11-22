package com.damarazka.quran.core.domain.repository

import androidx.lifecycle.LiveData
import com.damarazka.quran.core.data.Resource
import com.damarazka.quran.core.data.lokal.LocationPreferences
import com.damarazka.quran.core.domain.model.City
import kotlinx.coroutines.flow.Flow

interface IAdzanRepository {
    fun getLocation() : LiveData<List<String>>
    fun searchCity(city : String) : Flow<Resource<List<City>>>
}