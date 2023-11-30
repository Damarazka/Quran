package com.damarazka.quran.core.domain.repository

import androidx.lifecycle.LiveData
import com.damarazka.quran.core.data.Resource
import com.damarazka.quran.core.data.lokal.LocationPreferences
import com.damarazka.quran.core.domain.model.City
import com.damarazka.quran.core.domain.model.Jadwal
import kotlinx.coroutines.flow.Flow
import java.time.Month
import java.time.Year

interface IAdzanRepository {
    fun getLocation(): LiveData<List<String>>
    fun searchCity(city: String): Flow<Resource<List<City>>>
    fun getDailyAdzanTime(
        id: String,
        year: String,
        month: String,
        date: String
    ): Flow<Resource<Jadwal>>
}