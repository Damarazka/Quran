package com.damarazka.quran.core.di

import android.content.Context
import com.damarazka.quran.core.data.AdzanRepository
import com.damarazka.quran.core.data.network.RemoteDataSource
import com.damarazka.quran.core.data.QuranRepository
import com.damarazka.quran.core.data.lokal.Calenderpreferences
import com.damarazka.quran.core.data.lokal.LocationPreferences
import com.damarazka.quran.core.data.network.ApiConfig

object Injection {
    val quranApiService = ApiConfig.quranApiService
    val adzanApiService = ApiConfig.adzanApiService
    val remoteDataSource = RemoteDataSource(quranApiService, adzanApiService)
    fun provideQuranRepository(): QuranRepository {
        return QuranRepository(remoteDataSource)
    }


    fun provideAdzanRepository(context: Context): AdzanRepository{
        val locationPreferences = LocationPreferences(context)
        val calendarPreferences = Calenderpreferences()
        return AdzanRepository(remoteDataSource, locationPreferences,calendarPreferences )
    }
}