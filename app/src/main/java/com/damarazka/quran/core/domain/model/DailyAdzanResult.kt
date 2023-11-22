package com.damarazka.quran.core.domain.model

import com.damarazka.quran.core.data.Resource

data class DailyAdzanResult(
    val listAddress : List<String>,
    val listCity: Resource<List<City>>
)
