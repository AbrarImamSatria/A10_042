package com.example.perkebunan.repository

import com.example.perkebunan.model.CatatanPanen
import com.example.perkebunan.service_api.CatatanPanenService
import java.io.IOException

interface CatatanPanenRepository {
    suspend fun insertCatatanPanen(catatanPanen: CatatanPanen)

    suspend fun getCatatanPanen(): List<CatatanPanen>

    suspend fun updateCatatanPanen(idPanen: String, catatanPanen: CatatanPanen)

    suspend fun deleteCatatanPanen(idPanen: String)

    suspend fun getCatatanPanenbyIdPanen(idPanen: String): CatatanPanen
}

class NetworkCatatanPanenRepository(
    private val catatanpanenApiService: CatatanPanenService
) : CatatanPanenRepository {
    override suspend fun insertCatatanPanen(catatanPanen: CatatanPanen) {
        catatanpanenApiService.insertCatatanPanen(catatanPanen)
    }

    override suspend fun getCatatanPanen(): List<CatatanPanen> =
        catatanpanenApiService.getAllCatatanPanen()


    override suspend fun updateCatatanPanen(idPanen: String, catatanPanen: CatatanPanen) {
        catatanpanenApiService.updateCatatanPanen(idPanen, catatanPanen)
    }

    override suspend fun deleteCatatanPanen(idPanen: String) {
        try {
            val response = catatanpanenApiService.deleteCatatanPanen(idPanen)
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


    override suspend fun getCatatanPanenbyIdPanen(idPanen: String): CatatanPanen {
        return catatanpanenApiService.getCatatanPanenbyIdPanen(idPanen)
    }

}