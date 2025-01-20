package com.example.perkebunan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tanaman(
    @SerialName("id_tanaman")
    val idTanaman: String,
    @SerialName("nama_tanaman")
    val namaTanaman: String,
    @SerialName("periode_tanam")
    val periodeTanam: String,
    @SerialName("deskripsi_tanaman")
    val deskripsiTanaman: String,
)
