package com.example.perkebunan.service_api

import com.example.perkebunan.model.AktivitasPertanian
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AktivitasPertanianService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("insertaktivitaspertanian.php")
    suspend fun insertAktivitasPertanian(@Body aktivitasPertanian: AktivitasPertanian)

    @GET("bacaaktivitaspertanian.php")
    suspend fun getAllAktivitasPertanian(): List<AktivitasPertanian>

    @GET("baca1aktivitaspertanian.php/{id_aktivitas}")
    suspend fun getAktivitasPertanianbyIdAktivitas(@Query("id_aktivitas") idaktivitas:String): AktivitasPertanian

    @PUT("editaktivitaspertanian.php/{id_aktivitas}")
    suspend fun updateAktivitasPertanian(@Query("id_aktivitas")idAktivitas:String, @Body aktivitasPertanian: AktivitasPertanian)

    @DELETE("deleteaktivitaspertanian.php/{id_aktivitas}")
    suspend fun deleteAktivitasPertanian(@Query("id_aktivitas") idAktivitas: String): Response<Void>
}