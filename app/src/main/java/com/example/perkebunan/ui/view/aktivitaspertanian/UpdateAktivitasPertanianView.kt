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
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.UpdateAktivitasPertanianUiEvent
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.UpdateAktivitasPertanianUiState

@Composable
fun EditBodyAktivitasPertanian(
    updateAktivitasPertanianUiState: UpdateAktivitasPertanianUiState,
    onAktValueChange: (UpdateAktivitasPertanianUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputAktivitasPertanianEdit(
            updateAktivitasPertanianUiEvent = updateAktivitasPertanianUiState.updateAktivitasPertanianUiEvent,
            onValueChange = onAktValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2CAC7C)
            )
        ) {
            Text(text = "Simpan Perubahan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputAktivitasPertanianEdit(
    updateAktivitasPertanianUiEvent: UpdateAktivitasPertanianUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateAktivitasPertanianUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateAktivitasPertanianUiEvent.idTanaman,
            onValueChange = { onValueChange(updateAktivitasPertanianUiEvent.copy(idTanaman = it)) },
            label = { Text("ID Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateAktivitasPertanianUiEvent.idPekerja,
            onValueChange = { onValueChange(updateAktivitasPertanianUiEvent.copy(idPekerja = it)) },
            label = { Text("ID Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateAktivitasPertanianUiEvent.tanggalAktivitas,
            onValueChange = { onValueChange(updateAktivitasPertanianUiEvent.copy(tanggalAktivitas = it)) },
            label = { Text("Tanggal Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateAktivitasPertanianUiEvent.deskripsiAktivitas,
            onValueChange = { onValueChange(updateAktivitasPertanianUiEvent.copy(deskripsiAktivitas = it)) },
            label = { Text("Deskripsi Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}