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

sealed class HomeCatatanPanenUiState{
    data class Succes(val catatanPanen: List<CatatanPanen>):HomeCatatanPanenUiState()
    object Error:HomeCatatanPanenUiState()
    object Loading:HomeCatatanPanenUiState()
}

class HomeCatatanPanenViewModel(private val ctpn: CatatanPanenRepository): ViewModel(){
    var ctpnUiState: HomeCatatanPanenUiState by mutableStateOf(HomeCatatanPanenUiState.Loading)
        private set

    init {
        getCtpn()
    }

    fun getCtpn(){
        viewModelScope.launch {
            ctpnUiState = HomeCatatanPanenUiState.Loading
            ctpnUiState = try {
                HomeCatatanPanenUiState.Succes(ctpn.getCatatanPanen())
            }catch (e: IOException){
                HomeCatatanPanenUiState.Error
            }catch (e: HttpException){
                HomeCatatanPanenUiState.Error
            }
        }
    }

    fun deleteCtpn(idPanen: String){
        viewModelScope.launch {
            try {
                ctpn.deleteCatatanPanen(idPanen)
            }catch (e: IOException){
                HomeCatatanPanenUiState.Error
            }catch (e: HttpException){
                HomeCatatanPanenUiState.Error
            }
        }
    }
}