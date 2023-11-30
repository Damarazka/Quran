package com.damarazka.quran.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.damarazka.quran.core.data.lokal.Calenderpreferences
import com.damarazka.quran.core.data.lokal.LocationPreferences
import com.damarazka.quran.core.data.network.NetworkBoundResource
import com.damarazka.quran.core.data.network.NetworkResponse
import com.damarazka.quran.core.data.network.RemoteDataSource
import com.damarazka.quran.core.domain.model.City
import com.damarazka.quran.core.domain.model.DailyAdzanResult
import com.damarazka.quran.core.domain.repository.IAdzanRepository
import com.damarazka.quran.core.data.network.adzan.CityItem
import com.damarazka.quran.core.data.network.adzan.JadwalItem
import com.damarazka.quran.core.domain.model.Jadwal
import com.damarazka.quran.util.DataMapper
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.PrimitiveIterator

class AdzanRepository(
    private val remoteDataSource: RemoteDataSource,
    private val locationPreferences: LocationPreferences,
    private val calendarPreferences : Calenderpreferences
) : IAdzanRepository {
    override fun getLocation(): LiveData<List<String>> = locationPreferences.getKnownLastLocation()

    override fun searchCity(city: String): Flow<Resource<List<City>>> {
        return object : NetworkBoundResource<List<City>, List<CityItem>>() {
            override fun fetchFromNetwork(data: List<CityItem>): Flow<List<City>> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<NetworkResponse<List<CityItem>>> {
                return remoteDataSource.searchCity(city)
            }
        }.asFlow()
    }

    override fun getDailyAdzanTime(
        id: String,
        year: String,
        month: String,
        date: String
    ): Flow<Resource<Jadwal>> {
        return object : NetworkBoundResource<Jadwal, JadwalItem>() {
            override fun fetchFromNetwork(data: JadwalItem): Flow<Jadwal> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<NetworkResponse<JadwalItem>> {
                return remoteDataSource.getDailyAdzanTime(id, year, month, date)
            }
        }.asFlow()
    }

    fun getDailyAdzanTimeResult(): LiveData<Resource<DailyAdzanResult>> {
        val listLocation = getLocation()
        val listCity = listLocation.switchMap { location ->
            searchCity(location[0]).asLiveData()
        }

        val currentDate = calendarPreferences.getCurrentDate()
        val year = currentDate[0]
        val month = currentDate[1]
        val date = currentDate[2]

        val adzanTime = listCity.switchMap {
            val id = it.data?.get(0)?.idCity
            if (id != null) getDailyAdzanTime(id, year, month, date).asLiveData()
            else getDailyAdzanTime("1301", year, month, date).asLiveData()
        }
        val resultData = MediatorLiveData<Resource<DailyAdzanResult>>()

        resultData.addSource(listLocation) {
            resultData.value = combineLatesData(listLocation, adzanTime,currentDate)
        }
        resultData.addSource(adzanTime) {
            resultData.value = combineLatesData(listLocation, adzanTime, currentDate)
        }

        return resultData
    }

    private fun combineLatesData(
        listLocationResult: LiveData<List<String>>,
        adzanTimeResult: LiveData<Resource<Jadwal>>,
        currentDate : List<String>
    ): Resource<DailyAdzanResult> {
        val listLocation = listLocationResult.value
        val adzanTime = adzanTimeResult.value

        return if (listLocation == null || adzanTime == null) {
            Resource.Loading()
        } else {
            try {
                val data = DailyAdzanResult(listLocation, adzanTime, currentDate)
                Resource.Success(data)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }
    }
}