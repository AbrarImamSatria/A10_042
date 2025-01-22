package com.example.perkebunan.ui.viewmodel.pekerja

import com.example.perkebunan.model.Pekerja

data class InsertPekerjaUiState(
    val insertPekerjaUiEvent: InsertPekerjaUiEvent = InsertPekerjaUiEvent()
)

data class InsertPekerjaUiEvent(
    val idPekerja: String="",
    val namaPekerja: String="",
    val jabatan: String="",
    val kontakPekerja: String=""
)

fun InsertPekerjaUiEvent.toTnm(): Pekerja = Pekerja(
    idPekerja = idPekerja,
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)

fun Pekerja.toUiStatePkj(): InsertPekerjaUiState = InsertPekerjaUiState(
    insertPekerjaUiEvent = toInsertPekerjaUiEvent()
)

fun Pekerja.toInsertPekerjaUiEvent(): InsertPekerjaUiEvent = InsertPekerjaUiEvent(
    idPekerja = idPekerja,
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)