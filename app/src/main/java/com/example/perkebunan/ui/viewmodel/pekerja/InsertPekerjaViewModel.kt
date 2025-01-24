package com.example.perkebunan.ui.viewmodel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.Pekerja
import com.example.perkebunan.repository.PekerjaRepository
import kotlinx.coroutines.launch

class InsertPekerjaViewModel(private val pkj: PekerjaRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPekerjaUiState())
        private set

    fun updateInsertPkjState(insertPekerjaUiEvent: InsertPekerjaUiEvent) {
        uiState = uiState.copy(insertPekerjaUiEvent = insertPekerjaUiEvent)
    }

    fun insertPkj() {
        // Validate fields before attempting to insert
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    pkj.insertPekerja(uiState.insertPekerjaUiEvent.toPkj())
                    // Update UI state to show success snackbar
                    uiState = uiState.copy(
                        snackbarMessage = "Pekerja berhasil disimpan",
                        isSnackbarVisible = true
                    )
                } catch (e: Exception) {
                    // Update UI state to show error snackbar
                    uiState = uiState.copy(
                        snackbarMessage = "Gagal menyimpan Pekerja: ${e.localizedMessage}",
                        isSnackbarVisible = true
                    )
                    e.printStackTrace()
                }
            }
        }
    }

    // Reset snackbar visibility after showing
    fun resetSnackbarState() {
        uiState = uiState.copy(
            isSnackbarVisible = false,
            snackbarMessage = ""
        )
    }

    fun validateFields(): Boolean {
        val event = uiState.insertPekerjaUiEvent
        val errorState = FormErrorState(
            idPekerja = if (event.idPekerja.isNotEmpty()) null else "ID Pekerja tidak boleh kosong",
            namaPekerja = if (event.namaPekerja.isNotEmpty()) null else "Nama Pekerja tidak boleh kosong",
            jabatan = if (event.jabatan.isNotEmpty()) null else "Jabatan tidak boleh kosong",
            kontakPekerja = if (event.kontakPekerja.isNotEmpty()) null else "Kontak Pekerja tidak boleh kosong"
        )

        // Update UI state with error state
        uiState = uiState.copy(formErrorState = errorState)

        return errorState.isValid()
    }
}

data class FormErrorState(
    val idPekerja: String? = null,
    val namaPekerja: String? = null,
    val jabatan: String? = null,
    val kontakPekerja: String? = null
) {
    fun isValid(): Boolean =
        idPekerja == null &&
                namaPekerja == null &&
                jabatan == null &&
                kontakPekerja == null
}

data class InsertPekerjaUiState(
    val insertPekerjaUiEvent: InsertPekerjaUiEvent = InsertPekerjaUiEvent(),
    val formErrorState: FormErrorState = FormErrorState(),
    val isSnackbarVisible: Boolean = false,
    val snackbarMessage: String = ""
)

data class InsertPekerjaUiEvent(
    val idPekerja: String="",
    val namaPekerja: String="",
    val jabatan: String="",
    val kontakPekerja: String=""
)

fun InsertPekerjaUiEvent.toPkj(): Pekerja = Pekerja(
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