package com.damarazka.quran.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiService {
    @GET ("surah")
    fun getListSurah() : Call<SurahResponse>

    @GET ("https://api.alquran.cloud/v1/surah/{number}/editions/quran-uthmani,ar.alafasy,id.indonesian")
    fun getListAyahs(
        @Path("number") number: Int
    ) : Call<AyahResponse>
}