package com.example.perkebunan.ui.viewmodel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.perkebunan.model.Pekerja
import com.example.perkebunan.repository.PekerjaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePekerjaUiState{
    data class Succes(val pekerja: List<Pekerja>):HomePekerjaUiState()
    object Error:HomePekerjaUiState()
    object Loading:HomePekerjaUiState()
}

class HomePekerjaViewModel(private val pkj: PekerjaRepository): ViewModel(){
    var pkjUiState: HomePekerjaUiState by mutableStateOf(HomePekerjaUiState.Loading)
        private set

    init {
        getPkj()
    }

    fun getPkj(){
        viewModelScope.launch {
            pkjUiState = HomePekerjaUiState.Loading
            pkjUiState = try {
                HomePekerjaUiState.Succes(pkj.getPekerja())
            }catch (e: IOException){
                HomePekerjaUiState.Error
            }catch (e: HttpException){
                HomePekerjaUiState.Error
            }
        }
    }

    fun deletePkj(idPekerja: String){
        viewModelScope.launch {
            try {
                pkj.deletePekerja(idPekerja)
            }catch (e: IOException){
                HomePekerjaUiState.Error
            }catch (e: HttpException){
                HomePekerjaUiState.Error
            }
        }
    }
}
