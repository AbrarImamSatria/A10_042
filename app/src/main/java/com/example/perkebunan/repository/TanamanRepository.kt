package com.example.perkebunan.repository

import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.service_api.TanamanService
import java.io.IOException

interface TanamanRepository {
    suspend fun insertTanaman(tanaman: Tanaman)

    suspend fun getTanaman(): List<Tanaman>

    suspend fun updateTanaman(idTanaman: String, tanaman: Tanaman)

    suspend fun deleteTanaman(idTanaman: String)

    suspend fun getTanamanbyIdTanaman(idTanaman: String): Tanaman
}

class NetworkTanamanRepository(
    private val tanamanApiService: TanamanService
) : TanamanRepository {
    override suspend fun insertTanaman(tanaman: Tanaman) {
        tanamanApiService.insertTanaman(tanaman)
    }

    override suspend fun getTanaman(): List<Tanaman> =
        tanamanApiService.getAllTanaman()


    override suspend fun updateTanaman(idTanaman: String, tanaman: Tanaman) {
        tanamanApiService.updateTanaman(idTanaman, tanaman)
    }

    override suspend fun deleteTanaman(idTanaman: String) {
        try {
            val response = tanamanApiService.deleteTanaman(idTanaman)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete tanaman. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun getTanamanbyIdTanaman(idTanaman: String): Tanaman {
        return tanamanApiService.getTanamanbyIdTanaman(idTanaman)
    }
}