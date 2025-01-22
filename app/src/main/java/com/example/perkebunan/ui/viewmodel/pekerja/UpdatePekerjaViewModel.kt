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
        uiState = UpdatePekerjaUiState(updatePekerjaUiEvent = updatePekerjaUiEvent)
    }

    suspend fun updatePkj(idPekerja: String) {
        viewModelScope.launch {
            try {
                pkj.updatePekerja(idPekerja, uiState.updatePekerjaUiEvent.toPkj())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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

data class UpdatePekerjaUiState(
    val updatePekerjaUiEvent: UpdatePekerjaUiEvent = UpdatePekerjaUiEvent()
)

data class UpdatePekerjaUiEvent(
    val idPekerja: String="",
    val namaPekerja: String="",
    val jabatan: String="",
    val kontakPekerja: String=""
)

fun UpdatePekerjaUiEvent.toPkj(): Pekerja = Pekerja(
    idPekerja = idPekerja,
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)

fun Pekerja.toUpdatePekerjaUiState(): UpdatePekerjaUiState = UpdatePekerjaUiState(
    updatePekerjaUiEvent = toUpdatePekerjaUiEvent()
)

fun Pekerja.toUpdatePekerjaUiEvent(): UpdatePekerjaUiEvent = UpdatePekerjaUiEvent(
    idPekerja = idPekerja,
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)