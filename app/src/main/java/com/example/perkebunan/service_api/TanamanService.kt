package com.example.perkebunan.service_api

import com.example.perkebunan.model.Tanaman
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TanamanService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("inserttanaman.php")
    suspend fun insertTanaman(@Body tanaman: Tanaman)

    @GET("bacatanaman.php")
    suspend fun getAllTanaman(): List<Tanaman>

    @GET("baca1tanaman.php/{id_tanaman}")
    suspend fun getTanamanbyIdTanaman(@Query("id_tanaman") idtanaman:String):Tanaman

    @PUT("edittanaman.php/{id_tanaman}")
    suspend fun updateTanaman(@Query("id_tanaman")idTanaman:String, @Body tanaman: Tanaman)

    @DELETE("deletetanaman.php/{id_tanaman}")
    suspend fun deleteTanaman(@Query("id_tanaman") idTanaman:String): Response<Void>
}