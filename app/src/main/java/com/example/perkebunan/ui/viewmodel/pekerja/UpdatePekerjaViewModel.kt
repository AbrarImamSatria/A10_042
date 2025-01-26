package com.example.perkebunan.ui.viewmodel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.Pekerja
import com.example.perkebunan.repository.PekerjaRepository
import kotlinx.coroutines.launch

class UpdatePekerjaViewModel(private val pkj: PekerjaRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdatePekerjaUiState())
        private set

    fun updatePekerjaUiState(updatePekerjaUiEvent: UpdatePekerjaUiEvent) {
        uiState = uiState.copy(updatePekerjaUiEvent = updatePekerjaUiEvent)
    }

    suspend fun updatePkj(idPekerja: String) {
        if (validateFields()) {
            try {
                pkj.updatePekerja(idPekerja, uiState.updatePekerjaUiEvent.toPkj())
                uiState = uiState.copy(
                    snackbarMessage = "Pekerja berhasil diperbarui",
                    isSnackbarVisible = true
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    snackbarMessage = "Gagal memperbarui Pekerja: ${e.localizedMessage}",
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
        val event = uiState.updatePekerjaUiEvent
        val errorState = FormErrorStateUpdatePekerja(
            namaPekerja = if (event.namaPekerja.isNotEmpty()) null else "Nama Pekerja tidak boleh kosong",
            jabatan = if (event.jabatan.isNotEmpty()) null else "Jabatan tidak boleh kosong",
            kontakPekerja = if (event.kontakPekerja.isNotEmpty()) null else "Kontak Pekerja tidak boleh kosong"
        )

        uiState = uiState.copy(formErrorState = errorState)

        return errorState.isValid()
    }

    suspend fun getPekerjabyIdPekerja(idPekerja: String) {
        viewModelScope.launch {
            try {
                val result = pkj.getPekerjabyIdPekerja(idPekerja)
                uiState = result.toUpdatePekerjaUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class FormErrorStateUpdatePekerja(
    val namaPekerja: String? = null,
    val jabatan: String? = null,
    val kontakPekerja: String? = null
) {
    fun isValid(): Boolean =
        namaPekerja == null &&
                jabatan == null &&
                kontakPekerja == null
}

data class UpdatePekerjaUiState(
    val updatePekerjaUiEvent: UpdatePekerjaUiEvent = UpdatePekerjaUiEvent(),
    val formErrorState: FormErrorStateUpdatePekerja = FormErrorStateUpdatePekerja(),
    val isSnackbarVisible: Boolean = false,
    val snackbarMessage: String = ""
)

data class UpdatePekerjaUiEvent(
    val idPekerja: String="",
    val namaPekerja: String="",
    val jabatan: String="",
    val kontakPekerja: String=""
)

fun UpdatePekerjaUiEvent.toPkj(): Pekerja = Pekerja(
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)

fun Pekerja.toUpdatePekerjaUiState(): UpdatePekerjaUiState = UpdatePekerjaUiState(
    updatePekerjaUiEvent = toUpdatePekerjaUiEvent()
)

fun Pekerja.toUpdatePekerjaUiEvent(): UpdatePekerjaUiEvent = UpdatePekerjaUiEvent(
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)