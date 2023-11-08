package com.damarazka.quran.core.di

import com.damarazka.quran.core.data.QuranRemoteDataSource
import com.damarazka.quran.core.data.QuranRepository
import com.damarazka.quran.core.network.ApiConfig

object Injection {
    fun provideQuranRepository(): QuranRepository {
        val quranApiService = ApiConfig.quranApiService
        val quranRemoteDataSource = QuranRemoteDataSource(quranApiService)
        return QuranRepository(quranRemoteDataSource)
    }

}