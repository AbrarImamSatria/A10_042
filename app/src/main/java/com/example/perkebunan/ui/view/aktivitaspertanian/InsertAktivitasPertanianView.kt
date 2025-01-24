package com.example.perkebunan.ui.view.aktivitaspertanian

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
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.FormErrorState
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.InsertAktivitasPertanianUiEvent
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.InsertAktivitasPertanianUiState
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.InsertAktivitasPertanianViewModel
import com.example.perkebunan.ui.widget.DynamicSelectTextField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiEntryAktivitasPertanian: DestinasiNavigasi {
    override val route = "item_entryAktivitasPertanian"
    override val titleRes = "Entry Aktivitas Pertanian"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAktScreen(
    navigateBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAktivitasPertanianViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
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
                title = DestinasiEntryAktivitasPertanian.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){ innerPadding ->
        EntryBody(
            insertAktivitasPertanianUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertAktState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFields()) {
                        viewModel.insertAkt()
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
    insertAktivitasPertanianUiState: InsertAktivitasPertanianUiState,
    onSiswaValueChange: (InsertAktivitasPertanianUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAktivitasPertanianViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertAktivitasPertanianUiEvent = insertAktivitasPertanianUiState.insertAktivitasPertanianUiEvent,
            formErrorState = insertAktivitasPertanianUiState.formErrorState,
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

@Composable
fun FormInput(
    insertAktivitasPertanianUiEvent: InsertAktivitasPertanianUiEvent,
    formErrorState: FormErrorState,
    modifier: Modifier = Modifier,
    onValueChange: (InsertAktivitasPertanianUiEvent) -> Unit = {},
    enabled: Boolean = true,
    viewModel: InsertAktivitasPertanianViewModel
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertAktivitasPertanianUiEvent.idAktivitas,
            onValueChange = {onValueChange(insertAktivitasPertanianUiEvent.copy(idAktivitas = it))},
            label = { Text("ID Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorState.idAktivitas != null,
            supportingText = { formErrorState.idAktivitas?.let { Text(text = it, color = MaterialTheme.colorScheme.error) } }
        )

        DynamicSelectTextField(
            selectedValue = insertAktivitasPertanianUiEvent.idTanaman,
            options = viewModel.tanamanList.map { it.namaTanaman },
            label = "Pilih Tanaman",
            onValueChangedEvent = { selectedNama ->
                onValueChange(insertAktivitasPertanianUiEvent.copy(idTanaman = selectedNama))
            },
            isError = formErrorState.idTanaman != null,
            errorMessage = formErrorState.idTanaman
        )

        DynamicSelectTextField(
            selectedValue = insertAktivitasPertanianUiEvent.idPekerja,
            options = viewModel.pekerjaList.map { it.namaPekerja },
            label = "Pilih Pekerja",
            onValueChangedEvent = { selectedNama ->
                onValueChange(insertAktivitasPertanianUiEvent.copy(idPekerja = selectedNama))
            },
            isError = formErrorState.idPekerja != null,
            errorMessage = formErrorState.idPekerja
        )

        OutlinedTextField(
            value = insertAktivitasPertanianUiEvent.tanggalAktivitas,
            onValueChange = {onValueChange(insertAktivitasPertanianUiEvent.copy(tanggalAktivitas = it))},
            label = { Text("Tanggal Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorState.tanggalAktivitas != null,
            supportingText = {
                formErrorState.tanggalAktivitas?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        OutlinedTextField(
            value = insertAktivitasPertanianUiEvent.deskripsiAktivitas,
            onValueChange = {onValueChange(insertAktivitasPertanianUiEvent.copy(deskripsiAktivitas = it))},
            label = { Text("Deskripsi Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorState.deskripsiAktivitas != null,
            supportingText = {
                formErrorState.deskripsiAktivitas?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
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