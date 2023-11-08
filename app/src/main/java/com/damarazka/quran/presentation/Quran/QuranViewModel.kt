package com.damarazka.quran.presentation.Quran

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.damarazka.quran.core.data.QuranRepository
import com.damarazka.quran.core.data.Resource
import com.damarazka.quran.core.domain.model.QuranEdition
import com.damarazka.quran.core.domain.model.Surah
import com.damarazka.quran.core.network.ApiConfig
import com.damarazka.quran.core.network.quran.AyahResponse
import com.damarazka.quran.core.network.quran.SurahResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuranViewModel(private val quranRepository: QuranRepository) : ViewModel() {
    fun getListSurah(): LiveData<Resource<List<Surah>>> = quranRepository.getListSurah().asLiveData()

    fun getDetailSurahWithQuranEdition(number: Int): LiveData<Resource<List<QuranEdition>>> =
        quranRepository.getDetailSurahWithQuranEdition(number).asLiveData()
}