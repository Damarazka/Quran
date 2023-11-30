package com.damarazka.quran.core.data.network.adzan

import retrofit2.http.GET
import retrofit2.http.Path

interface AdzanApiService {
    @GET("sholat/kota/cari/{keyword}")
    suspend fun searchCity(
        @Path("keyword") city : String
    ): CityResponses

    @GET("sholat/jadwal/{idCity}/{tahun}/{bulan}/{tanggal}")
    suspend fun getDailyAdzanTime(
        @Path("idCity") id : String,
        @Path("tahun") year : String,
        @Path("bulan") month : String,
        @Path("tanggal") date : String
    ) : JadwalResponse
}