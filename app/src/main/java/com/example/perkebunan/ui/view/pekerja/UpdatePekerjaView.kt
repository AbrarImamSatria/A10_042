package com.example.perkebunan.ui.view.pekerja

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
import com.example.perkebunan.ui.viewmodel.pekerja.UpdatePekerjaUiEvent
import com.example.perkebunan.ui.viewmodel.pekerja.UpdatePekerjaUiState

@Composable
fun EditBodyPekerja(
    updatePekerjaUiState: UpdatePekerjaUiState,
    onPkjValueChange: (UpdatePekerjaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPekerjaEdit(
            updatePekerjaUiEvent = updatePekerjaUiState.updatePekerjaUiEvent,
            onValueChange = onPkjValueChange,
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
fun FormInputPekerjaEdit(
    updatePekerjaUiEvent: UpdatePekerjaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePekerjaUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updatePekerjaUiEvent.namaPekerja,
            onValueChange = { onValueChange(updatePekerjaUiEvent.copy(namaPekerja = it)) },
            label = { Text("Nama Pekerjan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updatePekerjaUiEvent.jabatan,
            onValueChange = { onValueChange(updatePekerjaUiEvent.copy(jabatan = it)) },
            label = { Text("Jabatan Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updatePekerjaUiEvent.kontakPekerja,
            onValueChange = { onValueChange(updatePekerjaUiEvent.copy(kontakPekerja = it)) },
            label = { Text("Kontak Pekerja") },
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