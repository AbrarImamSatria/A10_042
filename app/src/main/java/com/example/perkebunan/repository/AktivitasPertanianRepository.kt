package com.example.perkebunan.repository

import com.example.perkebunan.model.AktivitasPertanian
import com.example.perkebunan.service_api.AktivitasPertanianService
import java.io.IOException

interface AktivitasPertanianRepository {
    suspend fun insertAktivitasPetanian(aktivitasPertanian: AktivitasPertanian)

    suspend fun getAktivitasPertanian(): List<AktivitasPertanian>

    suspend fun updateAktivitasPertanian(idAktivitas: String, aktivitasPertanian: AktivitasPertanian)

    suspend fun deleteAktivitasPertanian(idAktivitas: String)

    suspend fun getAktivitasPertanianbyIdAktivitas(idAktivitas: String): AktivitasPertanian
}

class NetworkAktivitasPertanianRepository(
    private val aktivitaspertanianApiService: AktivitasPertanianService
) : AktivitasPertanianRepository {
    override suspend fun insertAktivitasPetanian(aktivitasPertanian: AktivitasPertanian) {
        aktivitaspertanianApiService.insertAktivitasPertanian(aktivitasPertanian)
    }

    override suspend fun getAktivitasPertanian(): List<AktivitasPertanian> =
        aktivitaspertanianApiService.getAllAktivitasPertanian()

    override suspend fun updateAktivitasPertanian(idAktivitas: String, aktivitasPertanian: AktivitasPertanian) {
        aktivitaspertanianApiService.updateAktivitasPertanian(idAktivitas, aktivitasPertanian)
    }

    override suspend fun deleteAktivitasPertanian(idAktivitas: String) {
        try {
            val response = aktivitaspertanianApiService.deleteAktivitasPertanian(idAktivitas)
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

    override suspend fun getAktivitasPertanianbyIdAktivitas(idAktivitas: String): AktivitasPertanian {
        return aktivitaspertanianApiService.getAktivitasPertanianbyIdAktivitas(idAktivitas)
    }
}