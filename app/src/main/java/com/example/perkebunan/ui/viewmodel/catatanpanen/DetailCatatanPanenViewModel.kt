package com.example.perkebunan.ui.viewmodel.catatanpanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.perkebunan.model.CatatanPanen
import com.example.perkebunan.repository.CatatanPanenRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailCatatanPanenUiState {
    data class Success(val catatanPanen: CatatanPanen) : DetailCatatanPanenUiState()
    object Error : DetailCatatanPanenUiState()
    object Loading : DetailCatatanPanenUiState()
}

class DetailCatatanPanenViewModel(private val ctpnRepository: CatatanPanenRepository) : ViewModel() {
    var ctpnDetailUiState: DetailCatatanPanenUiState by mutableStateOf(DetailCatatanPanenUiState.Loading)
        private set

    fun getCatatanPanenbyIdPanen(idPanen: String) {
        viewModelScope.launch {
            ctpnDetailUiState = DetailCatatanPanenUiState.Loading
            ctpnDetailUiState = try {
                val catatanPanen = ctpnRepository.getCatatanPanenbyIdPanen(idPanen)
                DetailCatatanPanenUiState.Success(catatanPanen)
            } catch (e: IOException) {
                DetailCatatanPanenUiState.Error
            } catch (e: HttpException) {
                DetailCatatanPanenUiState.Error
            }
        }
    }
}