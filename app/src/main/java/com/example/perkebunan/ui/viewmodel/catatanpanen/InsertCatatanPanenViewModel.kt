package com.example.perkebunan.ui.viewmodel.catatanpanen

import com.example.perkebunan.model.CatatanPanen
import com.example.perkebunan.model.Pekerja

data class InsertCatatanPanenUiState(
    val insertCatatanPanenUiEvent: InsertCatatanPanenUiEvent = InsertCatatanPanenUiEvent()
)

data class InsertCatatanPanenUiEvent(
    val idPanen: String="",
    val idTanaman: String="",
    val tanggalPanen: String="",
    val jumlahPanen: String="",
    val keterangan: String=""
)

fun InsertCatatanPanenUiEvent.toCtpn(): CatatanPanen = CatatanPanen(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan
)

fun CatatanPanen.toUiStateCtpn(): InsertCatatanPanenUiState = InsertCatatanPanenUiState(
    insertCatatanPanenUiEvent = toInsertCatatanPanenUiEvent()
)

fun CatatanPanen.toInsertCatatanPanenUiEvent(): InsertCatatanPanenUiEvent = InsertCatatanPanenUiEvent(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan
)