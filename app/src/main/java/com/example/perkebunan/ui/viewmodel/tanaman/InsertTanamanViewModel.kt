package com.example.perkebunan.ui.viewmodel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.repository.TanamanRepository
import kotlinx.coroutines.launch

class InsertTanamanViewModel(private val tnm: TanamanRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertTanamanUiState())
        private set

    fun updateInsertTnmState(insertTanamanUiEvent: InsertTanamanUiEvent) {
        uiState = uiState.copy(insertTanamanUiEvent = insertTanamanUiEvent)
    }

    fun insertTnm() {
        // Validate fields before attempting to insert
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    tnm.insertTanaman(uiState.insertTanamanUiEvent.toTnm())
                    // Update UI state to show success snackbar
                    uiState = uiState.copy(
                        snackbarMessage = "Tanaman berhasil disimpan",
                        isSnackbarVisible = true
                    )
                } catch (e: Exception) {
                    // Update UI state to show error snackbar
                    uiState = uiState.copy(
                        snackbarMessage = "Gagal menyimpan Tanaman: ${e.localizedMessage}",
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
        val event = uiState.insertTanamanUiEvent
        val errorState = FormErrorState(
            namaTanaman = if (event.namaTanaman.isNotEmpty()) null else "Nama Tanaman tidak boleh kosong",
            periodeTanam = if (event.periodeTanam.isNotEmpty()) null else "Periode Tanam tidak boleh kosong",
            deskripsiTanaman = if (event.deskripsiTanaman.isNotEmpty()) null else "Deskripsi Tanaman tidak boleh kosong"
        )

        // Update UI state with error state
        uiState = uiState.copy(formErrorState = errorState)

        return errorState.isValid()
    }
}

data class FormErrorState(
    val namaTanaman: String? = null,
    val periodeTanam: String? = null,
    val deskripsiTanaman: String? = null
) {
    fun isValid(): Boolean =
        namaTanaman == null &&
                periodeTanam == null &&
                deskripsiTanaman == null
}

data class InsertTanamanUiState(
    val insertTanamanUiEvent: InsertTanamanUiEvent = InsertTanamanUiEvent(),
    val formErrorState: FormErrorState = FormErrorState(),
    val isSnackbarVisible: Boolean = false,
    val snackbarMessage: String = ""
)

data class InsertTanamanUiEvent(
    val namaTanaman: String = "",
    val periodeTanam: String = "",
    val deskripsiTanaman: String = ""
)

fun InsertTanamanUiEvent.toTnm(): Tanaman = Tanaman(
    namaTanaman = namaTanaman,
    periodeTanam = periodeTanam,
    deskripsiTanaman = deskripsiTanaman
)

fun Tanaman.toUiStateTnm(): InsertTanamanUiState = InsertTanamanUiState(
    insertTanamanUiEvent = toInsertTanamanUiEvent()
)

fun Tanaman.toInsertTanamanUiEvent(): InsertTanamanUiEvent = InsertTanamanUiEvent(
    namaTanaman = namaTanaman,
    periodeTanam = periodeTanam,
    deskripsiTanaman = deskripsiTanaman
)