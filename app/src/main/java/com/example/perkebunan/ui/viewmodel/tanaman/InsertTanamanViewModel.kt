package com.example.perkebunan.ui.viewmodel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.repository.TanamanRepository
import kotlinx.coroutines.launch

class InsertTanamanViewModel (private val tnm: TanamanRepository): ViewModel(){
    var uiState by mutableStateOf(InsertTanamanUiState())
        private set

    fun updateInsertTnmState(insertTanamanUiEvent: InsertTanamanUiEvent){
        uiState = InsertTanamanUiState(insertTanamanUiEvent = insertTanamanUiEvent)
    }

    suspend fun insertTnm(){
        viewModelScope.launch {
            try {
                tnm.insertTanaman(uiState.insertTanamanUiEvent.toTnm())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertTanamanUiState(
    val insertTanamanUiEvent: InsertTanamanUiEvent = InsertTanamanUiEvent()
)

data class InsertTanamanUiEvent(
    val idTanaman: String="",
    val namaTanaman: String="",
    val periodeTanam: String="",
    val deskripsiTanaman: String=""
)

fun InsertTanamanUiEvent.toTnm():Tanaman = Tanaman(
    idTanaman = idTanaman,
    namaTanaman = namaTanaman,
    periodeTanam = periodeTanam,
    deskripsiTanaman = deskripsiTanaman
)

fun Tanaman.toUiStateTnm(): InsertTanamanUiState = InsertTanamanUiState(
    insertTanamanUiEvent = toInsertTanamanUiEvent()
)

fun Tanaman.toInsertTanamanUiEvent(): InsertTanamanUiEvent = InsertTanamanUiEvent(
    idTanaman = idTanaman,
    namaTanaman = namaTanaman,
    periodeTanam = periodeTanam,
    deskripsiTanaman = deskripsiTanaman
)