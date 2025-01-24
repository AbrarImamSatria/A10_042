package com.example.perkebunan.ui.viewmodel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.repository.TanamanRepository
import kotlinx.coroutines.launch

class UpdateTanamanViewModel(private val tnm: TanamanRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdateTanamanUiState())
        private set

    fun updateTanamanUiState(updateTanamanUiEvent: UpdateTanamanUiEvent) {
        uiState = uiState.copy(updateTanamanUiEvent = updateTanamanUiEvent)
    }

    suspend fun updateTnm(idTanaman: String) {
        if (validateFields()) {
            try {
                tnm.updateTanaman(idTanaman, uiState.updateTanamanUiEvent.toTnm())
                uiState = uiState.copy(
                    snackbarMessage = "Tanaman berhasil diperbarui",
                    isSnackbarVisible = true
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    snackbarMessage = "Gagal memperbarui Tanaman: ${e.localizedMessage}",
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
        val event = uiState.updateTanamanUiEvent
        val errorState = FormErrorStateUpdate(
            namaTanaman = if (event.namaTanaman.isNotEmpty()) null else "Nama Tanaman tidak boleh kosong",
            periodeTanam = if (event.periodeTanam.isNotEmpty()) null else "Periode Tanam tidak boleh kosong",
            deskripsiTanaman = if (event.deskripsiTanaman.isNotEmpty()) null else "Deskripsi Tanaman tidak boleh kosong"
        )

        uiState = uiState.copy(formErrorState = errorState)

        return errorState.isValid()
    }

    suspend fun getTanamanbyIdTanaman(idTanaman: String) {
        viewModelScope.launch {
            try {
                val result = tnm.getTanamanbyIdTanaman(idTanaman)
                uiState = result.toUpdateTanamanUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class FormErrorStateUpdate(
    val namaTanaman: String? = null,
    val periodeTanam: String? = null,
    val deskripsiTanaman: String? = null
) {
    fun isValid(): Boolean =
        namaTanaman == null &&
                periodeTanam == null &&
                deskripsiTanaman == null
}

data class UpdateTanamanUiState(
    val updateTanamanUiEvent: UpdateTanamanUiEvent = UpdateTanamanUiEvent(),
    val formErrorState: FormErrorStateUpdate = FormErrorStateUpdate(),
    val isSnackbarVisible: Boolean = false,
    val snackbarMessage: String = ""
)

data class UpdateTanamanUiEvent(
    val idTanaman: String="",
    val namaTanaman: String="",
    val periodeTanam: String="",
    val deskripsiTanaman: String=""
)

fun UpdateTanamanUiEvent.toTnm(): Tanaman = Tanaman(
    idTanaman = idTanaman,
    namaTanaman = namaTanaman,
    periodeTanam = periodeTanam,
    deskripsiTanaman = deskripsiTanaman
)

fun Tanaman.toUpdateTanamanUiState(): UpdateTanamanUiState = UpdateTanamanUiState(
    updateTanamanUiEvent = toUpdateTanamanUiEvent()
)

fun Tanaman.toUpdateTanamanUiEvent(): UpdateTanamanUiEvent = UpdateTanamanUiEvent(
    idTanaman = idTanaman,
    namaTanaman = namaTanaman,
    periodeTanam = periodeTanam,
    deskripsiTanaman = deskripsiTanaman
)