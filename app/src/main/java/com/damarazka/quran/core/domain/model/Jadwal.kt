package com.damarazka.quran.core.domain.model

import com.squareup.moshi.Json

data class Jadwal(
    val date: String? = null,
    val imsak: String? = null,
    val isya: String? = null,
    val dzuhur: String? = null,
    val subuh: String? = null,
    val dhuha: String? = null,
    val terbit: String? = null,
    val tanggal: String? = null,
    val ashar: String? = null,
    val maghrib: String? = null
)
