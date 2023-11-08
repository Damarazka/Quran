package com.damarazka.quran.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.damarazka.quran.core.di.Injection
import com.damarazka.quran.presentation.Quran.QuranViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(QuranViewModel::class.java) -> {
                QuranViewModel(Injection.provideQuranRepository()) as T
            }
            else -> throw Throwable("Unknown ViewModel Class: " + modelClass.name)
        }
}