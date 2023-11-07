package com.damarazka.quran.domain.repository

import com.damarazka.quran.data.Resource
import com.damarazka.quran.domain.model.QuranEdition
import com.damarazka.quran.domain.model.Surah
import kotlinx.coroutines.flow.Flow

interface IQuranRepository {
    fun getListSurah(): Flow<Resource<List<Surah>>>

    fun getDetailSurahWithQuranEdition(number: Int) : Flow<Resource<List<QuranEdition>>>

}