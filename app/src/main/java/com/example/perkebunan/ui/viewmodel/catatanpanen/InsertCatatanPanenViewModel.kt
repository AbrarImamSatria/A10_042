package com.example.perkebunan.ui.viewmodel.catatanpanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.CatatanPanen
import com.example.perkebunan.repository.CatatanPanenRepository
import kotlinx.coroutines.launch

class InsertCatatanPanenViewModel (private val ctpn: CatatanPanenRepository): ViewModel(){
    var uiState by mutableStateOf(InsertCatatanPanenUiState())
        private set

    fun updateInsertCtpnState(insertCatatanPanenUiEvent: InsertCatatanPanenUiEvent){
        uiState = InsertCatatanPanenUiState(insertCatatanPanenUiEvent = insertCatatanPanenUiEvent)
    }

    suspend fun insertCtpn(){
        viewModelScope.launch {
            try {
                ctpn.insertCatatanPanen(uiState.insertCatatanPanenUiEvent.toCtpn())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertCatatanPanenUiState(
    val insertCatatanPanenUiEvent: InsertCatatanPanenUiEvent = InsertCatatanPanenUiEvent()
)

data class InsertCatatanPanenUiEvent(
    val idPanen: String="",
    val idTanaman: String="",
    val tanggalPanen: String="",
    val jumlahPanen: String="",
    val keterangan: String=""
)

fun InsertCatatanPanenUiEvent.toCtpn(): CatatanPanen = CatatanPanen(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan
)

fun CatatanPanen.toUiStateCtpn(): InsertCatatanPanenUiState = InsertCatatanPanenUiState(
    insertCatatanPanenUiEvent = toInsertCatatanPanenUiEvent()
)

fun CatatanPanen.toInsertCatatanPanenUiEvent(): InsertCatatanPanenUiEvent = InsertCatatanPanenUiEvent(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan
)