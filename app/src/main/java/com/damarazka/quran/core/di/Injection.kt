package com.damarazka.quran.core.di

import com.damarazka.quran.core.data.network.RemoteDataSource
import com.damarazka.quran.core.data.QuranRepository
import com.damarazka.quran.core.network.ApiConfig

object Injection {
    fun provideQuranRepository(): QuranRepository {
        val quranApiService = ApiConfig.quranApiService
        val quranRemoteDataSource = RemoteDataSource(quranApiService)
        return QuranRepository(quranRemoteDataSource)
    }

}