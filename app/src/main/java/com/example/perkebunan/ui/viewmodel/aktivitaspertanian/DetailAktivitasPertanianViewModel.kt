package com.example.perkebunan.ui.viewmodel.aktivitaspertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.perkebunan.model.AktivitasPertanian
import com.example.perkebunan.repository.AktivitasPertanianRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailAktivitasPertanianUiState {
    data class Success(val aktivitasPertanian: AktivitasPertanian) : DetailAktivitasPertanianUiState()
    object Error : DetailAktivitasPertanianUiState()
    object Loading : DetailAktivitasPertanianUiState()
}

class DetailAktivitasPertanianViewModel(private val aktRepository: AktivitasPertanianRepository) : ViewModel() {
    var aktDetailUiState: DetailAktivitasPertanianUiState by mutableStateOf(DetailAktivitasPertanianUiState.Loading)
        private set

    fun getAktivitasPertanianbyIdAktivitas(idAktivitas: String) {
        viewModelScope.launch {
            aktDetailUiState = DetailAktivitasPertanianUiState.Loading
            aktDetailUiState = try {
                val aktivitasPertanian = aktRepository.getAktivitasPertanianbyIdAktivitas(idAktivitas)
                DetailAktivitasPertanianUiState.Success(aktivitasPertanian)
            } catch (e: IOException) {
                DetailAktivitasPertanianUiState.Error
            } catch (e: HttpException) {
                DetailAktivitasPertanianUiState.Error
            }
        }
    }
}