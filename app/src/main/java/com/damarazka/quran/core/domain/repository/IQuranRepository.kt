package com.damarazka.quran.core.domain.repository

import com.damarazka.quran.core.data.Resource
import com.damarazka.quran.core.domain.model.QuranEdition
import com.damarazka.quran.core.domain.model.Surah
import kotlinx.coroutines.flow.Flow

interface IQuranRepository {
    fun getListSurah(): Flow<Resource<List<Surah>>>

    fun getDetailSurahWithQuranEdition(number: Int) : Flow<Resource<List<QuranEdition>>>

}