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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.InsertAktivitasPertanianViewModel
import com.example.perkebunan.ui.viewmodel.catatanpanen.FormErrorState
import com.example.perkebunan.ui.viewmodel.catatanpanen.InsertCatatanPanenUiEvent
import com.example.perkebunan.ui.viewmodel.catatanpanen.InsertCatatanPanenUiState
import com.example.perkebunan.ui.viewmodel.catatanpanen.InsertCatatanPanenViewModel
import com.example.perkebunan.ui.widget.DynamicSelectTextField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiEntryCatatanPanen: DestinasiNavigasi {
    override val route = "item_entryCatatanPanen"
    override val titleRes = "Entry Catatan Panen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryCtpnScreen(
    navigateBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertCatatanPanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Observe snackbar message
    LaunchedEffect(viewModel.uiState.isSnackbarVisible, viewModel.uiState.snackbarMessage) {
        viewModel.uiState.snackbarMessage.takeIf { it.isNotEmpty() }?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackbarState()
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryCatatanPanen.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        EntryBody(
            insertCatatanPanenUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertCtpnState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFields()) {
                        viewModel.insertCtpn()
                        delay(600)
                        withContext(Dispatchers.Main) {
                            onNavigate()
                        }
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            viewModel = viewModel
        )
    }
}

@Composable
fun EntryBody(
    insertCatatanPanenUiState: InsertCatatanPanenUiState,
    onSiswaValueChange: (InsertCatatanPanenUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    viewModel: InsertCatatanPanenViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertCatatanPanenUiEvent = insertCatatanPanenUiState.insertCatatanPanenUiEvent,
            formErrorState = insertCatatanPanenUiState.formErrorState,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth(),
            viewModel = viewModel
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
    formErrorState: FormErrorState,
    modifier: Modifier = Modifier,
    onValueChange: (InsertCatatanPanenUiEvent) -> Unit = {},
    enabled: Boolean = true,
    viewModel: InsertCatatanPanenViewModel
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DynamicSelectTextField(
            selectedValue = insertCatatanPanenUiEvent.idTanaman,
            options = viewModel.tanamanList.map { it.namaTanaman },
            label = "Pilih Tanaman",
            onValueChangedEvent = { selectedNama ->
                onValueChange(insertCatatanPanenUiEvent.copy(idTanaman = selectedNama))
            },
            isError = formErrorState.idTanaman != null,
            errorMessage = formErrorState.idTanaman
        )

        OutlinedTextField(
            value = insertCatatanPanenUiEvent.tanggalPanen,
            onValueChange = { onValueChange(insertCatatanPanenUiEvent.copy(tanggalPanen = it)) },
            label = { Text("Tanggal Panen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorState.tanggalPanen != null,
            supportingText = {
                formErrorState.tanggalPanen?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = insertCatatanPanenUiEvent.jumlahPanen,
            onValueChange = { onValueChange(insertCatatanPanenUiEvent.copy(jumlahPanen = it)) },
            label = { Text("Jumlah Panen") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorState.jumlahPanen != null,
            supportingText = {
                formErrorState.jumlahPanen?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = insertCatatanPanenUiEvent.keterangan,
            onValueChange = { onValueChange(insertCatatanPanenUiEvent.copy(keterangan = it)) },
            label = { Text("Keterangan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorState.keterangan != null,
            supportingText = {
                formErrorState.keterangan?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding((12.dp))
        )
    }
}