package com.damarazka.quran.data

import com.damarazka.quran.domain.model.QuranEdition
import com.damarazka.quran.domain.model.Surah
import com.damarazka.quran.domain.repository.IQuranRepository
import com.damarazka.quran.network.quran.QuranEditionItem
import com.damarazka.quran.network.quran.SurahItem
import com.damarazka.quran.util.DataMapper
import kotlinx.coroutines.flow.Flow

class QuranRepository(private val remoteDataSource: QuranRemoteDataSource) : IQuranRepository {
    override fun getListSurah(): Flow<Resource<List<Surah>>> {
        return object : NetworkBoundResource<List<Surah>, List<SurahItem>>(){
            override fun fetchFromNetwork(data: List<SurahItem>): Flow<List<Surah>> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<NetworkResponse<List<SurahItem>>> {
                return remoteDataSource.getListSurah()
            }
        }.asFlow()
    }

    override fun getDetailSurahWithQuranEdition(number: Int): Flow<Resource<List<QuranEdition>>> {
        return object : NetworkBoundResource<List<QuranEdition>, List<QuranEditionItem>>(){
            override fun fetchFromNetwork(data: List<QuranEditionItem>): Flow<List<QuranEdition>> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<NetworkResponse<List<QuranEditionItem>>> {
                return remoteDataSource.getDetailSurahWithQuranEditions(number)
            }
        }.asFlow()
    }
}