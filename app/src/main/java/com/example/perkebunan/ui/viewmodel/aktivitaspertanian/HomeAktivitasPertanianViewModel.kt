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

sealed class HomeAktivitasPertanianUiState{
    data class Succes(val aktivitasPertanian: List<AktivitasPertanian>):HomeAktivitasPertanianUiState()
    object Error:HomeAktivitasPertanianUiState()
    object Loading:HomeAktivitasPertanianUiState()
}

class HomeAktivitasPertanianViewModel(private val akt: AktivitasPertanianRepository): ViewModel(){
    var aktUiState: HomeAktivitasPertanianUiState by mutableStateOf(HomeAktivitasPertanianUiState.Loading)
        private set

    init {
        getAkt()
    }

    fun getAkt(){
        viewModelScope.launch {
            aktUiState = HomeAktivitasPertanianUiState.Loading
            aktUiState = try {
                HomeAktivitasPertanianUiState.Succes(akt.getAktivitasPertanian())
            }catch (e: IOException){
                HomeAktivitasPertanianUiState.Error
            }catch (e: HttpException){
                HomeAktivitasPertanianUiState.Error
            }
        }
    }

    fun deleteAkt(idAktivitas: String){
        viewModelScope.launch {
            try {
                akt.deleteAktivitasPertanian(idAktivitas)
            }catch (e: IOException){
                HomeAktivitasPertanianUiState.Error
            }catch (e: HttpException){
                HomeAktivitasPertanianUiState.Error
            }
        }
    }
}