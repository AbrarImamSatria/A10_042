package com.example.perkebunan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AktivitasPertanian(
    @SerialName("id_aktivitas")
    val idAktivitas: Int = 0,
    @SerialName("id_tanaman")
    val idTanaman: String,
    @SerialName("id_pekerja")
    val idPekerja: String,
    @SerialName("tanggal_aktivitas")
    val tanggalAktivitas: String,
    @SerialName("deskripsi_aktivitas")
    val deskripsiAktivitas: String,
)
