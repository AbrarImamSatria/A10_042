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
import com.example.perkebunan.ui.viewmodel.catatanpanen.UpdateCatatanPanenUiEvent
import com.example.perkebunan.ui.viewmodel.catatanpanen.UpdateCatatanPanenUiState

@Composable
fun EditBodyCatatanPanen(
    updateCatatanPanenUiState: UpdateCatatanPanenUiState,
    onCtpnValueChange: (UpdateCatatanPanenUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputCatatanPanenEdit(
            updateCatatanPanenUiEvent = updateCatatanPanenUiState.updateCatatanPanenUiEvent,
            onValueChange = onCtpnValueChange,
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
fun FormInputCatatanPanenEdit(
    updateCatatanPanenUiEvent: UpdateCatatanPanenUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateCatatanPanenUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateCatatanPanenUiEvent.idTanaman,
            onValueChange = { onValueChange(updateCatatanPanenUiEvent.copy(idTanaman = it)) },
            label = { Text("ID Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateCatatanPanenUiEvent.tanggalPanen,
            onValueChange = { onValueChange(updateCatatanPanenUiEvent.copy(tanggalPanen = it)) },
            label = { Text("Tanggal Panen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateCatatanPanenUiEvent.jumlahPanen,
            onValueChange = { onValueChange(updateCatatanPanenUiEvent.copy(jumlahPanen = it)) },
            label = { Text("Jumlah Panen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateCatatanPanenUiEvent.keterangan,
            onValueChange = { onValueChange(updateCatatanPanenUiEvent.copy(keterangan = it)) },
            label = { Text("Keterangan") },
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