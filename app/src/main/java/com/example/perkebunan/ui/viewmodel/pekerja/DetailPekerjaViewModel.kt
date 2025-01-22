package com.example.perkebunan.ui.viewmodel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.perkebunan.model.Pekerja
import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.repository.PekerjaRepository
import com.example.perkebunan.repository.TanamanRepository
import com.example.perkebunan.ui.viewmodel.tanaman.DetailTanamanUiState
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPekerjaUiState {
    data class Success(val pekerja: Pekerja) : DetailPekerjaUiState()
    object Error : DetailPekerjaUiState()
    object Loading : DetailPekerjaUiState()
}

class DetailPekerjaViewModel(private val pkjRepository: PekerjaRepository) : ViewModel() {
    var pkjDetailUiState: DetailPekerjaUiState by mutableStateOf(DetailPekerjaUiState.Loading)
        private set

    fun getPekerjabyIdPekerja(idPekerja: String) {
        viewModelScope.launch {
            pkjDetailUiState = DetailPekerjaUiState.Loading
            pkjDetailUiState = try {
                val pekerja = pkjRepository.getPekerjabyIdPekerja(idPekerja)
                DetailPekerjaUiState.Success(pekerja)
            } catch (e: IOException) {
                DetailPekerjaUiState.Error
            } catch (e: HttpException) {
                DetailPekerjaUiState.Error
            }
        }
    }
}