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

class UpdateCatatanPanenViewModel(
    private val ctpn: CatatanPanenRepository,
    private val tanamanRepository: TanamanRepository
) : ViewModel() {
    var uiState by mutableStateOf(UpdateCatatanPanenUiState())
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

    fun updateCatatanPanenUiState(updateCatatanPanenUiEvent: UpdateCatatanPanenUiEvent) {
        uiState = uiState.copy(updateCatatanPanenUiEvent = updateCatatanPanenUiEvent)
    }

    suspend fun updateCtpn(idPanen: String) {
        if (validateFields()) {
            try {
                ctpn.updateCatatanPanen(idPanen, uiState.updateCatatanPanenUiEvent.toCtpn())
                uiState = uiState.copy(
                    snackbarMessage = "Catatan Panen berhasil diperbarui",
                    isSnackbarVisible = true
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    snackbarMessage = "Gagal memperbarui Catatan Panen: ${e.localizedMessage}",
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
        val event = uiState.updateCatatanPanenUiEvent
        val errorState = FormErrorStateCatatanPanen(
            idTanaman = if (event.idTanaman.isNotEmpty()) null else "Tanaman tidak boleh kosong",
            tanggalPanen = if (event.tanggalPanen.isNotEmpty()) null else "Tanggal Panen tidak boleh kosong",
            jumlahPanen = if (event.jumlahPanen.isNotEmpty()) null else "Jumlah Panen tidak boleh kosong",
            keterangan = if (event.keterangan.isNotEmpty()) null else "Keterangan tidak boleh kosong"
        )

        uiState = uiState.copy(formErrorState = errorState)

        return errorState.isValid()
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

data class FormErrorStateCatatanPanen(
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

data class UpdateCatatanPanenUiState(
    val updateCatatanPanenUiEvent: UpdateCatatanPanenUiEvent = UpdateCatatanPanenUiEvent(),
    val formErrorState: FormErrorStateCatatanPanen = FormErrorStateCatatanPanen(),
    val isSnackbarVisible: Boolean = false,
    val snackbarMessage: String = ""
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