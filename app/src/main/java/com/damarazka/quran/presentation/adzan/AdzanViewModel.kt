package com.damarazka.quran.presentation.adzan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.damarazka.quran.core.data.AdzanRepository
import com.damarazka.quran.core.data.Resource
import com.damarazka.quran.core.domain.model.DailyAdzanResult

class AdzanViewModel(private val adzanRepository: AdzanRepository) : ViewModel() {
    fun getDailyAdzanTime(): LiveData<Resource<DailyAdzanResult>> =
        adzanRepository.getDailyAdzanTimeResult()


}