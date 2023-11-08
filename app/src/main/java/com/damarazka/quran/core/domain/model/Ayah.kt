package com.damarazka.quran.core.domain.model

import com.squareup.moshi.Json

data class Ayah(
    val number: Int? = null,

    val text: String? = null,

    val numberInSurah: Int? = null,

    val audio: String? = null
)
