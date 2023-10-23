package com.damarazka.quran.network

import retrofit2.Call
import retrofit2.http.GET

interface QuranApiService {
    @GET ("surah")
    fun getListSurah() : Call<SurahItem>
}