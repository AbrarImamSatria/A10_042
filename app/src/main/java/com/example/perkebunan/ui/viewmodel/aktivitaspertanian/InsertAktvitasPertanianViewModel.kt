package com.example.perkebunan.ui.viewmodel.aktivitaspertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.AktivitasPertanian
import com.example.perkebunan.repository.AktivitasPertanianRepository
import kotlinx.coroutines.launch

class InsertAktivitasPertanianViewModel(private val akt: AktivitasPertanianRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertAktivitasPertanianUiState())
        private set

    fun updateInsertAktState(insertAktivitasPertanianUiEvent: InsertAktivitasPertanianUiEvent) {
        uiState = uiState.copy(insertAktivitasPertanianUiEvent = insertAktivitasPertanianUiEvent)
    }

    fun insertAkt() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    akt.insertAktivitasPertanian(uiState.insertAktivitasPertanianUiEvent.toAkt())
                    uiState = uiState.copy(
                        snackbarMessage = "Aktivitas Pertanian berhasil disimpan",
                        isSnackbarVisible = true
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackbarMessage = "Gagal menyimpan Aktivitas Pertanian: ${e.localizedMessage}",
                        isSnackbarVisible = true
                    )
                    e.printStackTrace()
                }
            }
        }
    }

    fun resetSnackbarState() {
        uiState = uiState.copy(
            isSnackbarVisible = false,
            snackbarMessage = ""
        )
    }

    fun validateFields(): Boolean {
        val event = uiState.insertAktivitasPertanianUiEvent
        val errorState = FormErrorState(
            idAktivitas = if (event.idAktivitas.isNotEmpty()) null else "ID Aktivitas tidak boleh kosong",
            idTanaman = if (event.idTanaman.isNotEmpty()) null else "ID Tanaman tidak boleh kosong",
            idPekerja = if (event.idPekerja.isNotEmpty()) null else "ID Pekerja tidak boleh kosong",
            tanggalAktivitas = if (event.tanggalAktivitas.isNotEmpty()) null else "Tanggal Aktivitas tidak boleh kosong",
            deskripsiAktivitas = if (event.deskripsiAktivitas.isNotEmpty()) null else "Deskripsi Aktivitas tidak boleh kosong"
        )

        uiState = uiState.copy(formErrorState = errorState)

        return errorState.isValid()
    }
}

data class FormErrorState(
    val idAktivitas: String? = null,
    val idTanaman: String? = null,
    val idPekerja: String? = null,
    val tanggalAktivitas: String? = null,
    val deskripsiAktivitas: String? = null
) {
    fun isValid(): Boolean =
        idAktivitas == null &&
                idTanaman == null &&
                idPekerja == null &&
                tanggalAktivitas == null &&
                deskripsiAktivitas == null
}

data class InsertAktivitasPertanianUiState(
    val insertAktivitasPertanianUiEvent: InsertAktivitasPertanianUiEvent = InsertAktivitasPertanianUiEvent(),
    val formErrorState: FormErrorState = FormErrorState(),
    val isSnackbarVisible: Boolean = false,
    val snackbarMessage: String = ""
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