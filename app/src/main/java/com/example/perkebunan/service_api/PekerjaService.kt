package com.example.perkebunan.service_api

import com.example.perkebunan.model.Pekerja
import com.example.perkebunan.model.Tanaman
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PekerjaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("insertpekerja.php")
    suspend fun insertPekerja(@Body pekerja: Pekerja)

    @GET("bacapekerja.php")
    suspend fun getAllPekerja(): List<Pekerja>

    @GET("baca1pekerja.php/{id_pekerja}")
    suspend fun getPekerjabyIdPekerja(@Query("id_pekerja") idpekerja:String): Pekerja

    @PUT("editpekerja.php/{id_pekerja}")
    suspend fun updatePekerja(@Query("id_pekeja")idpekerja:String, @Body pekerja: Pekerja)

    @DELETE("deletepekerja.php/{id_pekerja}")
    suspend fun deletePekerja(@Query("id_pekerja") idPekerja:String): Response<Void>
}