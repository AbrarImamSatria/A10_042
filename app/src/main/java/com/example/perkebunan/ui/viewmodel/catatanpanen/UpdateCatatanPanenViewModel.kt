package com.example.perkebunan.ui.viewmodel.catatanpanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.CatatanPanen
import com.example.perkebunan.repository.CatatanPanenRepository
import kotlinx.coroutines.launch

class UpdateCatatanPanenViewModel(private val ctpn: CatatanPanenRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdateCatatanPanenUiState())
        private set

    fun updateCatatanPanenUiState(updateCatatanPanenUiEvent: UpdateCatatanPanenUiEvent) {
        uiState = UpdateCatatanPanenUiState(updateCatatanPanenUiEvent = updateCatatanPanenUiEvent)
    }

    suspend fun updateCtpn(idPanen: String) {
        viewModelScope.launch {
            try {
                ctpn.updateCatatanPanen(idPanen, uiState.updateCatatanPanenUiEvent.toCtpn())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getCatatanPanenbyIdPanen(idPanen: String) {
        viewModelScope.launch {
            try {
                val result = ctpn.getCatatanPanenbyIdPanen(idPanen)
                uiState = result.toUpdateCatatanPanenUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdateCatatanPanenUiState(
    val updateCatatanPanenUiEvent: UpdateCatatanPanenUiEvent = UpdateCatatanPanenUiEvent()
)

data class UpdateCatatanPanenUiEvent(
    val idPanen: String="",
    val idTanaman: String="",
    val tanggalPanen: String="",
    val jumlahPanen: String="",
    val keterangan: String=""
)

fun UpdateCatatanPanenUiEvent.toCtpn(): CatatanPanen = CatatanPanen(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan
)

fun CatatanPanen.toUpdateCatatanPanenUiState(): UpdateCatatanPanenUiState = UpdateCatatanPanenUiState(
    updateCatatanPanenUiEvent = toUpdateCatatanPanenUiEvent()
)

fun CatatanPanen.toUpdateCatatanPanenUiEvent(): UpdateCatatanPanenUiEvent = UpdateCatatanPanenUiEvent(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan
)