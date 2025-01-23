package com.example.perkebunan.ui.viewmodel.aktivitaspertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.AktivitasPertanian
import com.example.perkebunan.repository.AktivitasPertanianRepository
import kotlinx.coroutines.launch

class InsertAktivitasPertanianViewModel (private val akt: AktivitasPertanianRepository): ViewModel(){
    var uiState by mutableStateOf(InsertAktivitasPertanianUiState())
        private set

    fun updateInsertAktState(insertAktivitasPertanianUiEvent: InsertAktivitasPertanianUiEvent){
        uiState = InsertAktivitasPertanianUiState(insertAktivitasPertanianUiEvent = insertAktivitasPertanianUiEvent)
    }

    suspend fun insertAkt(){
        viewModelScope.launch {
            try {
                akt.insertAktivitasPertanian(uiState.insertAktivitasPertanianUiEvent.toAkt())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertAktivitasPertanianUiState(
    val insertAktivitasPertanianUiEvent: InsertAktivitasPertanianUiEvent = InsertAktivitasPertanianUiEvent()
)

data class InsertAktivitasPertanianUiEvent(
    val idAktivitas: String="",
    val idTanaman: String="",
    val idPekerja: String="",
    val tanggalAktivitas: String="",
    val deskripsiAktivitas: String=""
)

fun InsertAktivitasPertanianUiEvent.toAkt(): AktivitasPertanian = AktivitasPertanian(
    idAktivitas = idAktivitas,
    idTanaman = idTanaman,
    idPekerja = idPekerja,
    tanggalAktivitas = tanggalAktivitas,
    deskripsiAktivitas = deskripsiAktivitas
)

fun AktivitasPertanian.toUiStateAkt(): InsertAktivitasPertanianUiState = InsertAktivitasPertanianUiState(
    insertAktivitasPertanianUiEvent = toInsertAktivitasPertanianUiEvent()
)

fun AktivitasPertanian.toInsertAktivitasPertanianUiEvent(): InsertAktivitasPertanianUiEvent = InsertAktivitasPertanianUiEvent(
    idAktivitas = idAktivitas,
    idTanaman = idTanaman,
    idPekerja = idPekerja,
    tanggalAktivitas = tanggalAktivitas,
    deskripsiAktivitas = deskripsiAktivitas
)