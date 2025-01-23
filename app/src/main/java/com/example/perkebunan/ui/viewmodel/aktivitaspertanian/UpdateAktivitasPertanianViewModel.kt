package com.example.perkebunan.ui.viewmodel.aktivitaspertanian

import com.example.perkebunan.model.AktivitasPertanian

data class UpdateAktivitasPertanianUiState(
    val updateAktivitasPertanianUiEvent: UpdateAktivitasPertanianUiEvent = UpdateAktivitasPertanianUiEvent()
)

data class UpdateAktivitasPertanianUiEvent(
    val idAktivitas: String="",
    val idTanaman: String="",
    val idPekerja: String="",
    val tanggalAktivitas: String="",
    val deskripsiAktivitas: String=""
)

fun UpdateAktivitasPertanianUiEvent.toAkt(): AktivitasPertanian = AktivitasPertanian(
    idAktivitas = idAktivitas,
    idTanaman = idTanaman,
    idPekerja = idPekerja,
    tanggalAktivitas = tanggalAktivitas,
    deskripsiAktivitas = deskripsiAktivitas
)

fun AktivitasPertanian.toUpdateAktivitasPertanianUiState(): UpdateAktivitasPertanianUiState = UpdateAktivitasPertanianUiState(
    updateAktivitasPertanianUiEvent = toUpdateAktivitasPertanianUiEvent()
)

fun AktivitasPertanian.toUpdateAktivitasPertanianUiEvent(): UpdateAktivitasPertanianUiEvent = UpdateAktivitasPertanianUiEvent(
    idAktivitas = idAktivitas,
    idTanaman = idTanaman,
    idPekerja = idPekerja,
    tanggalAktivitas = tanggalAktivitas,
    deskripsiAktivitas = deskripsiAktivitas
)