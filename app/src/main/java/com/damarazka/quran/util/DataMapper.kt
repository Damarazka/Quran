package com.damarazka.quran.util

import android.webkit.JavascriptInterface
import com.damarazka.quran.core.domain.model.Ayah
import com.damarazka.quran.core.domain.model.City
import com.damarazka.quran.core.domain.model.QuranEdition
import com.damarazka.quran.core.domain.model.Surah
import com.damarazka.quran.core.data.network.adzan.CityItem
import com.damarazka.quran.core.data.network.adzan.JadwalItem
import com.damarazka.quran.core.data.network.quran.AyahsItem
import com.damarazka.quran.core.data.network.quran.QuranEditionItem
import com.damarazka.quran.core.data.network.quran.SurahItem
import com.damarazka.quran.core.domain.model.Jadwal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {

    @JvmName("mapSurahItemToSurah")
    fun mapResponseToDomain(input: List<SurahItem>): Flow<List<Surah>> {
        val listSurah = ArrayList<Surah>()
        input.map {
            val surah = Surah(
                number = it.number,
                englishName = it.englishName,
                numberOfAyahs = it.numberOfAyahs,
                revelationType = it.revelationType,
                name = it.name,
                englishNameTranslation = it.englishNameTranslation
            )
            listSurah.add(surah)
        }
        return flowOf(listSurah)
    }

    @JvmName("mapQuranEditionItemToQuranEdition")
    fun mapResponseToDomain(input: List<QuranEditionItem>): Flow<List<QuranEdition>> {
        val quranEdition = ArrayList<QuranEdition>()
        input.map {
            val edition = QuranEdition(
                number = it.number,
                englishName = it.englishName,
                numberOfAyahs = it.numberOfAyahs,
                revelationType = it.revelationType,
                name = it.name,
                englishNameTranslation = it.englishNameTranslation,
                ayahs = mapAyahsItemToDomain(it.ayahs)
            )
            quranEdition.add(edition)
        }
        return flowOf(quranEdition)
    }

    private fun mapAyahsItemToDomain(input: List<AyahsItem>): List<Ayah> {
        val listAyah = ArrayList<Ayah>()
        input.map {
            val ayah = Ayah(
                number = it.number,
                text = it.text,
                numberInSurah = it.numberInSurah,
                audio = it.audio
            )
            listAyah.add(ayah)
        }
        return listAyah
    }

    @JvmName("mapCityItemToDomain")
    fun mapResponseToDomain(input: List<CityItem>): Flow<List<City>> {
        val cities = ArrayList<City>()
        input.map {
            val city = City(
                idCity = it.id,
                location = it.lokasi
            )
            cities.add(city)
        }
        return flowOf(cities)
    }

    @JvmName("appJadwalItemToDomain")
    fun mapResponseToDomain(input: JadwalItem): Flow<Jadwal>{
        val jadwal = Jadwal(
         date = input.date,
         imsak = input.imsak,
         isya = input.isya,
         dzuhur = input.dzuhur,
         subuh = input.subuh,
         dhuha = input.dhuha,
         terbit = input.terbit,
         tanggal = input.tanggal,
         ashar = input.ashar,
         maghrib = input.maghrib
        )
        return flowOf(jadwal)
    }
}