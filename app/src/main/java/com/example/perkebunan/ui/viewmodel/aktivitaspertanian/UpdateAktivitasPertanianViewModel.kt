package com.example.perkebunan.ui.viewmodel.aktivitaspertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.AktivitasPertanian
import com.example.perkebunan.model.Pekerja
import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.repository.AktivitasPertanianRepository
import com.example.perkebunan.repository.PekerjaRepository
import com.example.perkebunan.repository.TanamanRepository
import kotlinx.coroutines.launch

class UpdateAktivitasPertanianViewModel(
    private val akt: AktivitasPertanianRepository,
    private val tanamanRepository: TanamanRepository,
    private val pekerjaRepository: PekerjaRepository
) : ViewModel() {
    var uiState by mutableStateOf(UpdateAktivitasPertanianUiState())
        private set
    var tanamanList by mutableStateOf(emptyList<Tanaman>())
        private set
    var pekerjaList by mutableStateOf(emptyList<Pekerja>())
        private set

    init {
        fetchTanamanList()
        fetchPekerjaList()
    }

    private fun fetchTanamanList() {
        viewModelScope.launch {
            tanamanList = tanamanRepository.getTanaman()
        }
    }

    private fun fetchPekerjaList() {
        viewModelScope.launch {
            pekerjaList = pekerjaRepository.getPekerja()
        }
    }

    fun updateAktivitasPertanianUiState(updateAktivitasPertanianUiEvent: UpdateAktivitasPertanianUiEvent) {
        uiState = uiState.copy(updateAktivitasPertanianUiEvent = updateAktivitasPertanianUiEvent)
    }

    suspend fun updateAkt(idAktivitas: String) {
        if (validateFields()) {
            try {
                akt.updateAktivitasPertanian(idAktivitas, uiState.updateAktivitasPertanianUiEvent.toAkt())
                uiState = uiState.copy(
                    snackbarMessage = "Aktivitas Pertanian berhasil diperbarui",
                    isSnackbarVisible = true
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    snackbarMessage = "Gagal memperbarui Aktivitas Pertanian: ${e.localizedMessage}",
                    isSnackbarVisible = true
                )
                e.printStackTrace()
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
        val event = uiState.updateAktivitasPertanianUiEvent
        val errorState = FormErrorStateUpdate(
            idTanaman = if (event.idTanaman.isNotEmpty()) null else "Tanaman tidak boleh kosong",
            idPekerja = if (event.idPekerja.isNotEmpty()) null else "Pekerja tidak boleh kosong",
            tanggalAktivitas = if (event.tanggalAktivitas.isNotEmpty()) null else "Tanggal Aktivitas tidak boleh kosong",
            deskripsiAktivitas = if (event.deskripsiAktivitas.isNotEmpty()) null else "Deskripsi Aktivitas tidak boleh kosong"
        )

        uiState = uiState.copy(formErrorState = errorState)

        return errorState.isValid()
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

data class FormErrorStateUpdate(
    val idTanaman: String? = null,
    val idPekerja: String? = null,
    val tanggalAktivitas: String? = null,
    val deskripsiAktivitas: String? = null
) {
    fun isValid(): Boolean =
        idTanaman == null &&
                idPekerja == null &&
                tanggalAktivitas == null &&
                deskripsiAktivitas == null
}

data class UpdateAktivitasPertanianUiState(
    val updateAktivitasPertanianUiEvent: UpdateAktivitasPertanianUiEvent = UpdateAktivitasPertanianUiEvent(),
    val formErrorState: FormErrorStateUpdate = FormErrorStateUpdate(),
    val isSnackbarVisible: Boolean = false,
    val snackbarMessage: String = ""
)

data class UpdateAktivitasPertanianUiEvent(
    val idAktivitas: String="",
    val idTanaman: String="",
    val idPekerja: String="",
    val tanggalAktivitas: String="",
    val deskripsiAktivitas: String=""
)

fun UpdateAktivitasPertanianUiEvent.toAkt(): AktivitasPertanian = AktivitasPertanian(
    idTanaman = idTanaman,
    idPekerja = idPekerja,
    tanggalAktivitas = tanggalAktivitas,
    deskripsiAktivitas = deskripsiAktivitas
)

fun AktivitasPertanian.toUpdateAktivitasPertanianUiState(): UpdateAktivitasPertanianUiState = UpdateAktivitasPertanianUiState(
    updateAktivitasPertanianUiEvent = toUpdateAktivitasPertanianUiEvent()
)

fun AktivitasPertanian.toUpdateAktivitasPertanianUiEvent(): UpdateAktivitasPertanianUiEvent = UpdateAktivitasPertanianUiEvent(
    idTanaman = idTanaman,
    idPekerja = idPekerja,
    tanggalAktivitas = tanggalAktivitas,
    deskripsiAktivitas = deskripsiAktivitas
)