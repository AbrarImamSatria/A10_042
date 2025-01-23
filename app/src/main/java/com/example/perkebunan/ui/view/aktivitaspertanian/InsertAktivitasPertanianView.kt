package com.example.perkebunan.ui.view.aktivitaspertanian

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
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.InsertAktivitasPertanianUiEvent
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.InsertAktivitasPertanianUiState

@Composable
fun EntryBody(
    insertAktivitasPertanianUiState: InsertAktivitasPertanianUiState,
    onSiswaValueChange: (InsertAktivitasPertanianUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertAktivitasPertanianUiEvent = insertAktivitasPertanianUiState.insertAktivitasPertanianUiEvent,
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
    insertAktivitasPertanianUiEvent: InsertAktivitasPertanianUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertAktivitasPertanianUiEvent)->Unit = {},
    enabled: Boolean = true
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertAktivitasPertanianUiEvent.idAktivitas,
            onValueChange = {onValueChange(insertAktivitasPertanianUiEvent.copy(idAktivitas = it))},
            label = { Text("ID Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertAktivitasPertanianUiEvent.idTanaman,
            onValueChange = {onValueChange(insertAktivitasPertanianUiEvent.copy(idTanaman = it))},
            label = { Text("ID Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertAktivitasPertanianUiEvent.idPekerja,
            onValueChange = {onValueChange(insertAktivitasPertanianUiEvent.copy(idPekerja = it))},
            label = { Text("ID Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertAktivitasPertanianUiEvent.tanggalAktivitas,
            onValueChange = {onValueChange(insertAktivitasPertanianUiEvent.copy(tanggalAktivitas = it))},
            label = { Text("Tanggal Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertAktivitasPertanianUiEvent.deskripsiAktivitas,
            onValueChange = {onValueChange(insertAktivitasPertanianUiEvent.copy(deskripsiAktivitas = it))},
            label = { Text("Deskripsi Aktivitas") },
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