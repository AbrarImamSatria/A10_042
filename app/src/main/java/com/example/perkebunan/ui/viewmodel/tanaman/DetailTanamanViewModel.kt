package com.example.perkebunan.ui.viewmodel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.repository.TanamanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailTanamanUiState {
    data class Success(val tanaman: Tanaman) : DetailTanamanUiState()
    object Error : DetailTanamanUiState()
    object Loading : DetailTanamanUiState()
}

class DetailTanamanViewModel(private val tnmRepository: TanamanRepository) : ViewModel() {
    var tnmDetailUiState: DetailTanamanUiState by mutableStateOf(DetailTanamanUiState.Loading)
        private set

    fun getTanamanbyIdTanaman(idTanaman: String) {
        viewModelScope.launch {
            tnmDetailUiState = DetailTanamanUiState.Loading
            tnmDetailUiState = try {
                val tanaman = tnmRepository.getTanamanbyIdTanaman(idTanaman)
                DetailTanamanUiState.Success(tanaman)
            } catch (e: IOException) {
                DetailTanamanUiState.Error
            } catch (e: HttpException) {
                DetailTanamanUiState.Error
            }
        }
    }
}