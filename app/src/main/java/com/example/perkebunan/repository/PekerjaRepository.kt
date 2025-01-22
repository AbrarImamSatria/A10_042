package com.example.perkebunan.repository

import com.example.perkebunan.model.Pekerja
import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.service_api.PekerjaService
import com.example.perkebunan.service_api.TanamanService
import java.io.IOException

interface PekerjaRepository {
    suspend fun insertPekerja(pekerja: Pekerja)

    suspend fun getPekerja(): List<Pekerja>

    suspend fun updatePekerja(idPekerja: String, pekerja: Pekerja)

    suspend fun deletePekerja(idPekerja: String)

    suspend fun getPekerjabyIdPekerja(idPekerja: String): Pekerja
}

class NetworkPekerjaRepository(
    private val pekerjaApiService: PekerjaService
) : PekerjaRepository {
    override suspend fun insertPekerja(pekerja: Pekerja) {
        pekerjaApiService.insertPekerja(pekerja)
    }

    override suspend fun getPekerja(): List<Pekerja> =
        pekerjaApiService.getAllPekerja()

    override suspend fun updatePekerja(idPekerja: String, pekerja: Pekerja) {
        pekerjaApiService.updatePekerja(idPekerja, pekerja)
    }

    override suspend fun deletePekerja(idPekerja: String) {
        try {
            val response = pekerjaApiService.deletePekerja(idPekerja)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete pekerja. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun getPekerjabyIdPekerja(idPekerja: String): Pekerja {
        return pekerjaApiService.getPekerjabyIdPekerja(idPekerja)
    }
}


