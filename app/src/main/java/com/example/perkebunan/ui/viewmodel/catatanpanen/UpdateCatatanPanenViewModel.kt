package com.example.perkebunan.ui.viewmodel.catatanpanen

import com.example.perkebunan.model.CatatanPanen

data class UpdateCatatanPanenUiState(
    val updateCatatanPanenUiEvent: UpdateCatatanPanenUiEvent = UpdateCatatanPanenUiEvent()
)

data class UpdateCatatanPanenUiEvent(
    val idPanen: String="",
    val idTanaman: String="",
    val tanggalPanen: String="",
    val jumlahPanen: String="",
    val keterangan: String=""
)

fun UpdateCatatanPanenUiEvent.toCtpn(): CatatanPanen = CatatanPanen(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan
)

fun CatatanPanen.toUpdateCatatanPanenUiState(): UpdateCatatanPanenUiState = UpdateCatatanPanenUiState(
    updateCatatanPanenUiEvent = toUpdateCatatanPanenUiEvent()
)

fun CatatanPanen.toUpdateCatatanPanenUiEvent(): UpdateCatatanPanenUiEvent = UpdateCatatanPanenUiEvent(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan
)