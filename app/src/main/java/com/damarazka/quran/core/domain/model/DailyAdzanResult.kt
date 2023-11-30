package com.damarazka.quran.core.domain.model

import com.damarazka.quran.core.data.Resource

data class DailyAdzanResult(
    val listLocation : List<String>,
    val adzanTime: Resource<Jadwal>,
    val currentDate : List<String>
)
