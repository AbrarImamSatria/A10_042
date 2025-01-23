package com.example.perkebunan.dependenciesinjection

import com.example.perkebunan.repository.AktivitasPertanianRepository
import com.example.perkebunan.repository.CatatanPanenRepository
import com.example.perkebunan.repository.NetworkAktivitasPertanianRepository
import com.example.perkebunan.repository.NetworkCatatanPanenRepository
import com.example.perkebunan.repository.NetworkPekerjaRepository
import com.example.perkebunan.repository.NetworkTanamanRepository
import com.example.perkebunan.repository.PekerjaRepository
import com.example.perkebunan.repository.TanamanRepository
import com.example.perkebunan.service_api.AktivitasPertanianService
import com.example.perkebunan.service_api.CatatanPanenService
import com.example.perkebunan.service_api.PekerjaService
import com.example.perkebunan.service_api.TanamanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val tanamanRepository: TanamanRepository
    val pekerjaRepository: PekerjaRepository
    val catatanPanenRepository: CatatanPanenRepository
    val aktivitasPertanianRepository: AktivitasPertanianRepository
}

class PerkebunanContainer:AppContainer{

    private val baseUrl = "http://10.0.2.2:80/perkebunanKU/"
    private val json = Json{ignoreUnknownKeys = true}
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    // TANAMAN
    private val tanamanService: TanamanService by lazy {
        retrofit.create(TanamanService::class.java)
    }

    override val tanamanRepository: TanamanRepository by lazy {
        NetworkTanamanRepository(tanamanService)
    }

    // PEKERJA
    private val pekerjaService: PekerjaService by lazy {
        retrofit.create(PekerjaService::class.java)
    }

    override val pekerjaRepository: PekerjaRepository by lazy {
        NetworkPekerjaRepository(pekerjaService)
    }

    // CATATAN PANEN
    private val catatanPanenService: CatatanPanenService by lazy {
        retrofit.create(CatatanPanenService::class.java)
    }

    override val catatanPanenRepository: CatatanPanenRepository by lazy {
        NetworkCatatanPanenRepository(catatanPanenService)
    }

    // AKTIVITAS PERTANIAN
    private val aktivitasPertanianService: AktivitasPertanianService by lazy {
        retrofit.create(AktivitasPertanianService::class.java)
    }

    override val aktivitasPertanianRepository: AktivitasPertanianRepository by lazy {
        NetworkAktivitasPertanianRepository(aktivitasPertanianService)
    }
}