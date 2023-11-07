package com.damarazka.quran.network.quran

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiService {
    @GET ("surah")
    suspend fun getListSurah() : SurahResponse

    @GET ("https://api.alquran.cloud/v1/surah/{number}/editions/quran-uthmani,ar.alafasy,id.indonesian")
    suspend fun getDetailSurahWithQuranEdition(
        @Path("number") number: Int
    ) : AyahResponse
}