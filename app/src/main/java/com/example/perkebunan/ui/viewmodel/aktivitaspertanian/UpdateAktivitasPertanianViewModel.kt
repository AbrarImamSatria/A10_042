package com.example.perkebunan.ui.viewmodel.aktivitaspertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.AktivitasPertanian
import com.example.perkebunan.repository.AktivitasPertanianRepository
import kotlinx.coroutines.launch

class UpdateAktivitasPertanianViewModel(private val akt: AktivitasPertanianRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdateAktivitasPertanianUiState())
        private set

    fun updateAktivitasPertanianUiState(updateAktivitasPertanianUiEvent: UpdateAktivitasPertanianUiEvent) {
        uiState = UpdateAktivitasPertanianUiState(updateAktivitasPertanianUiEvent = updateAktivitasPertanianUiEvent)
    }

    suspend fun updateAkt(idAktivitas: String) {
        viewModelScope.launch {
            try {
                akt.updateAktivitasPertanian(idAktivitas, uiState.updateAktivitasPertanianUiEvent.toAkt())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getAktivitasPertanianbyIdAktivitas(idAktivitas: String) {
        viewModelScope.launch {
            try {
                val result = akt.getAktivitasPertanianbyIdAktivitas(idAktivitas)
                uiState = result.toUpdateAktivitasPertanianUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

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