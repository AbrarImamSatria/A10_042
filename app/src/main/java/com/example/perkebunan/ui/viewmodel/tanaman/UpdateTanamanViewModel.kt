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
        uiState = UpdateTanamanUiState(updateTanamanUiEvent = updateTanamanUiEvent)
    }

    suspend fun updateTnm(idTanaman: String) {
        viewModelScope.launch {
            try {
                tnm.updateTanaman(idTanaman, uiState.updateTanamanUiEvent.toTnm())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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

data class UpdateTanamanUiState(
    val updateTanamanUiEvent: UpdateTanamanUiEvent = UpdateTanamanUiEvent()
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