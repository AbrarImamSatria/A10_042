package com.example.perkebunan.ui.viewmodel.catatanpanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.CatatanPanen
import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.repository.CatatanPanenRepository
import com.example.perkebunan.repository.TanamanRepository
import kotlinx.coroutines.launch

class InsertCatatanPanenViewModel(
    private val ctpn: CatatanPanenRepository,
    private val tanamanRepository: TanamanRepository
) : ViewModel() {
    var uiState by mutableStateOf(InsertCatatanPanenUiState())
        private set
    var tanamanList by mutableStateOf(emptyList<Tanaman>())
        private set

    init {
        fetchTanamanList()
    }

    private fun fetchTanamanList() {
        viewModelScope.launch {
            tanamanList = tanamanRepository.getTanaman()
        }
    }

    fun updateInsertCtpnState(insertCatatanPanenUiEvent: InsertCatatanPanenUiEvent) {
        uiState = uiState.copy(insertCatatanPanenUiEvent = insertCatatanPanenUiEvent)
    }

    fun insertCtpn() {
        // Validate fields before attempting to insert
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    ctpn.insertCatatanPanen(uiState.insertCatatanPanenUiEvent.toCtpn())
                    // Update UI state to show success snackbar
                    uiState = uiState.copy(
                        snackbarMessage = "Catatan Panen berhasil disimpan",
                        isSnackbarVisible = true
                    )
                } catch (e: Exception) {
                    // Update UI state to show error snackbar
                    uiState = uiState.copy(
                        snackbarMessage = "Gagal menyimpan Catatan Panen: ${e.localizedMessage}",
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
        val event = uiState.insertCatatanPanenUiEvent
        val errorState = FormErrorState(
            idTanaman = if (event.idTanaman.isNotEmpty()) null else "Tanaman tidak boleh kosong",
            tanggalPanen = if (event.tanggalPanen.isNotEmpty()) null else "Tanggal Panen tidak boleh kosong",
            jumlahPanen = if (event.jumlahPanen.isNotEmpty()) null else "Jumlah Panen tidak boleh kosong",
            keterangan = if (event.keterangan.isNotEmpty()) null else "Keterangan tidak boleh kosong"
        )

        // Update UI state with error state
        uiState = uiState.copy(formErrorState = errorState)

        return errorState.isValid()
    }
}

data class FormErrorState(
    val idTanaman: String? = null,
    val tanggalPanen: String? = null,
    val jumlahPanen: String? = null,
    val keterangan: String? = null
) {
    fun isValid(): Boolean =
        idTanaman == null &&
                tanggalPanen == null &&
                jumlahPanen == null &&
                keterangan == null
}

data class InsertCatatanPanenUiState(
    val insertCatatanPanenUiEvent: InsertCatatanPanenUiEvent = InsertCatatanPanenUiEvent(),
    val formErrorState: FormErrorState = FormErrorState(),
    val isSnackbarVisible: Boolean = false,
    val snackbarMessage: String = ""
)

data class InsertCatatanPanenUiEvent(
    val idTanaman: String="",
    val tanggalPanen: String="",
    val jumlahPanen: String="",
    val keterangan: String=""
)

fun InsertCatatanPanenUiEvent.toCtpn(): CatatanPanen = CatatanPanen(
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan
)

fun CatatanPanen.toUiStateCtpn(): InsertCatatanPanenUiState = InsertCatatanPanenUiState(
    insertCatatanPanenUiEvent = toInsertCatatanPanenUiEvent()
)

fun CatatanPanen.toInsertCatatanPanenUiEvent(): InsertCatatanPanenUiEvent = InsertCatatanPanenUiEvent(
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan
)