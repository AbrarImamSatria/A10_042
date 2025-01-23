package com.example.perkebunan.service_api

import com.example.perkebunan.model.CatatanPanen
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface CatatanPanenService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("insertcatatanpanen.php")
    suspend fun insertCatatanPanen(@Body catatanPanen: CatatanPanen)

    @GET("bacacatatanpanen.php")
    suspend fun getAllCatatanPanen(): List<CatatanPanen>

    @GET("baca1catatanpanen.php/{id_panen}")
    suspend fun getCatatanPanenbyIdPanen(@Query("id_panen") idpanen:String): CatatanPanen

    @PUT("editcatatanpanen.php/{id_panen}")
    suspend fun updateCatatanPanen(@Query("id_panen")idPanen:String, @Body catatanPanen: CatatanPanen)

    @DELETE("deletecatatanpanen.php/{id_panen}")
    suspend fun deleteCatatanPanen(@Query("id_panen") idPanen:String): Response<Void>
}