package com.example.perkebunan.ui.viewmodel.pekerja

import com.example.perkebunan.model.Pekerja
import com.example.perkebunan.model.Tanaman

sealed class HomePekerjaUiState{
    data class Succes(val pekerja: List<Pekerja>):HomePekerjaUiState()
    object Error:HomePekerjaUiState()
    object Loading:HomePekerjaUiState()
}

