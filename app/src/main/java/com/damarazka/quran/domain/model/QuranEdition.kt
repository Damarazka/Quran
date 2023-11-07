package com.damarazka.quran.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class QuranEdition(

    val number: Int? = null,

    val englishName: String? = null,

    val numberOfAyahs: Int? = null,

    val revelationType: String? = null,

    val name: String? = null,

    val ayahs: List<Ayah>,

    val englishNameTranslation: String? = null
)
