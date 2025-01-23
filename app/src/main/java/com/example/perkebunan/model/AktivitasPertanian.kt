package com.example.perkebunan.model

import kotlinx.serialization.SerialName

data class AktivitasPertanian(
    @SerialName("id_aktivitas")
    val idAktivitas: String,
    @SerialName("id_tanaman")
    val idTanaman: String,
    @SerialName("id_Pekerja")
    val idPekerja: String,
    @SerialName("tanggal_aktivitas")
    val tanggalAktivitas: String,
    @SerialName("deskripsi_aktivitas")
    val deskripsiAktivitas: String,
)
