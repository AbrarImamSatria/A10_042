package com.example.perkebunan.ui.view.tanaman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.perkebunan.ui.viewmodel.tanaman.UpdateTanamanUiEvent
import com.example.perkebunan.ui.viewmodel.tanaman.UpdateTanamanUiState

@Composable
fun EditBodyTanaman(
    updateTanamanUiState: UpdateTanamanUiState,
    onTnmValueChange: (UpdateTanamanUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTanamanEdit(
            updateTanamanUiEvent = updateTanamanUiState.updateTanamanUiEvent,
            onValueChange = onTnmValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan Perubahan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputTanamanEdit(
    updateTanamanUiEvent: UpdateTanamanUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateTanamanUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateTanamanUiEvent.idTanaman,
            onValueChange = { onValueChange(updateTanamanUiEvent.copy(idTanaman = it)) },
            label = { Text("ID Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateTanamanUiEvent.namaTanaman,
            onValueChange = { onValueChange(updateTanamanUiEvent.copy(idTanaman = it)) },
            label = { Text("ID Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateTanamanUiEvent.periodeTanam,
            onValueChange = { onValueChange(updateTanamanUiEvent.copy(idTanaman = it)) },
            label = { Text("ID Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateTanamanUiEvent.deskripsiTanaman,
            onValueChange = { onValueChange(updateTanamanUiEvent.copy(idTanaman = it)) },
            label = { Text("ID Tanaman") },
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