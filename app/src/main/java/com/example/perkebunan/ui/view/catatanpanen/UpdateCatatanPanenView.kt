package com.example.perkebunan.ui.view.catatanpanen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.catatanpanen.UpdateCatatanPanenUiEvent
import com.example.perkebunan.ui.viewmodel.catatanpanen.UpdateCatatanPanenUiState
import com.example.perkebunan.ui.viewmodel.catatanpanen.UpdateCatatanPanenViewModel
import kotlinx.coroutines.launch

object DestinasiEditCatatanPanen : DestinasiNavigasi {
    override val route = "CatatanPanen_edit/{idPanen}"
    override val titleRes = "Edit Catatan Panen"
    const val idCatatanPanenArg = "idPanen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreenCatatanPanen(
    navigateBack: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateCatatanPanenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    idPanen: String
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            viewModel.getCatatanPanenbyIdPanen(idPanen)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEditCatatanPanen.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EditBodyCatatanPanen(
            updateCatatanPanenUiState = viewModel.uiState,
            onCtpnValueChange = viewModel::updateCatatanPanenUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateCtpn(idPanen)
                    onNavigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

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