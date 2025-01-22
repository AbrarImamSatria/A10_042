package com.example.perkebunan.ui.viewmodel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.Pekerja
import com.example.perkebunan.repository.PekerjaRepository
import kotlinx.coroutines.launch

class InsertPekerjaViewModel (private val pkj: PekerjaRepository): ViewModel(){
    var uiState by mutableStateOf(InsertPekerjaUiState())
        private set

    fun updateInsertPkjState(insertPekerjaUiEvent: InsertPekerjaUiEvent){
        uiState = InsertPekerjaUiState(insertPekerjaUiEvent = insertPekerjaUiEvent)
    }

    suspend fun insertPkj(){
        viewModelScope.launch {
            try {
                pkj.insertPekerja(uiState.insertPekerjaUiEvent.toPkj())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertPekerjaUiState(
    val insertPekerjaUiEvent: InsertPekerjaUiEvent = InsertPekerjaUiEvent()
)

data class InsertPekerjaUiEvent(
    val idPekerja: String="",
    val namaPekerja: String="",
    val jabatan: String="",
    val kontakPekerja: String=""
)

fun InsertPekerjaUiEvent.toPkj(): Pekerja = Pekerja(
    idPekerja = idPekerja,
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)

fun Pekerja.toUiStatePkj(): InsertPekerjaUiState = InsertPekerjaUiState(
    insertPekerjaUiEvent = toInsertPekerjaUiEvent()
)

fun Pekerja.toInsertPekerjaUiEvent(): InsertPekerjaUiEvent = InsertPekerjaUiEvent(
    idPekerja = idPekerja,
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)