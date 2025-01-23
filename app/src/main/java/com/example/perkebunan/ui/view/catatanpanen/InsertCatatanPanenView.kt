package com.example.perkebunan.ui.view.catatanpanen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.perkebunan.ui.viewmodel.catatanpanen.InsertCatatanPanenUiEvent
import com.example.perkebunan.ui.viewmodel.catatanpanen.InsertCatatanPanenUiState

@Composable
fun EntryBody(
    insertCatatanPanenUiState: InsertCatatanPanenUiState,
    onSiswaValueChange: (InsertCatatanPanenUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertCatatanPanenUiEvent = insertCatatanPanenUiState.insertCatatanPanenUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A67E)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertCatatanPanenUiEvent: InsertCatatanPanenUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertCatatanPanenUiEvent)->Unit = {},
    enabled: Boolean = true
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertCatatanPanenUiEvent.idPanen,
            onValueChange = {onValueChange(insertCatatanPanenUiEvent.copy(idPanen = it))},
            label = { Text("ID Panen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertCatatanPanenUiEvent.idTanaman,
            onValueChange = {onValueChange(insertCatatanPanenUiEvent.copy(idTanaman = it))},
            label = { Text("ID Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertCatatanPanenUiEvent.tanggalPanen,
            onValueChange = {onValueChange(insertCatatanPanenUiEvent.copy(tanggalPanen = it))},
            label = { Text("Tanggal Panen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertCatatanPanenUiEvent.jumlahPanen,
            onValueChange = {onValueChange(insertCatatanPanenUiEvent.copy(jumlahPanen = it))},
            label = { Text("Jumlah Panen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertCatatanPanenUiEvent.keterangan,
            onValueChange = {onValueChange(insertCatatanPanenUiEvent.copy(keterangan = it))},
            label = { Text("Keterangan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled){
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding((12.dp))
        )
    }
}