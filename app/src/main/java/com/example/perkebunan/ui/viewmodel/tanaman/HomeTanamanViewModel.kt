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

sealed class HomeTanamanUiState{
    data class Succes(val tanaman: List<Tanaman>):HomeTanamanUiState()
    object Error:HomeTanamanUiState()
    object Loading:HomeTanamanUiState()
}

class HomeTanamanViewModel(private val tnm:TanamanRepository):ViewModel(){
    var tnmUiState: HomeTanamanUiState by mutableStateOf(HomeTanamanUiState.Loading)
        private set

    init {
        getTnm()
    }

    fun getTnm(){
        viewModelScope.launch {
            tnmUiState = HomeTanamanUiState.Loading
            tnmUiState = try {
                HomeTanamanUiState.Succes(tnm.getTanaman())
            }catch (e:IOException){
                HomeTanamanUiState.Error
            }catch (e:HttpException){
                HomeTanamanUiState.Error
            }
        }
    }

    fun deleteTnm(idTanaman: String){
        viewModelScope.launch {
            try {
                tnm.deleteTanaman(idTanaman)
            }catch (e:IOException){
                HomeTanamanUiState.Error
            }catch (e:HttpException){
                HomeTanamanUiState.Error
            }
        }
    }
}