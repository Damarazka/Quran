package com.damarazka.quran.di

import com.damarazka.quran.data.QuranRemoteDataSource
import com.damarazka.quran.data.QuranRepository
import com.damarazka.quran.network.ApiConfig

object Injection {
    fun provideQuranRepository(): QuranRepository{
        val quranApiService = ApiConfig.quranApiService
        val quranRemoteDataSource = QuranRemoteDataSource(quranApiService)
        return QuranRepository(quranRemoteDataSource)
    }

}