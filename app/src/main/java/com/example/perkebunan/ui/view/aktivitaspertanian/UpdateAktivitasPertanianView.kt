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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.FormErrorStateUpdate
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.UpdateAktivitasPertanianUiEvent
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.UpdateAktivitasPertanianUiState
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.UpdateAktivitasPertanianViewModel
import com.example.perkebunan.ui.widget.DynamicSelectTextField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiEditAktivitasPertanian : DestinasiNavigasi {
    override val route = "AktivitasPertanian_edit/{idAktivitas}"
    override val titleRes = "Edit Aktivitas Pertanian"
    const val idAktivitasPertanianArg = "idAktivitas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreenAktivitasPertanian(
    navigateBack: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateAktivitasPertanianViewModel = viewModel(factory = PenyediaViewModel.Factory),
    idAktivitas: String
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Observe snackbar message
    LaunchedEffect(viewModel.uiState.isSnackbarVisible, viewModel.uiState.snackbarMessage) {
        viewModel.uiState.snackbarMessage.takeIf { it.isNotEmpty() }?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackbarState()
            }
        }
    }

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            viewModel.getAktivitasPertanianbyIdAktivitas(idAktivitas)
        }
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEditAktivitasPertanian.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        EditBodyAktivitasPertanian(
            updateAktivitasPertanianUiState = viewModel.uiState,
            onAktValueChange = viewModel::updateAktivitasPertanianUiState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFields()) {
                        viewModel.updateAkt(idAktivitas)
                        delay(600)
                        withContext(Dispatchers.Main) {
                            onNavigateBack()
                        }
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            viewModel = viewModel
        )
    }
}

@Composable
fun EditBodyAktivitasPertanian(
    updateAktivitasPertanianUiState: UpdateAktivitasPertanianUiState,
    onAktValueChange: (UpdateAktivitasPertanianUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateAktivitasPertanianViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputAktivitasPertanianEdit(
            updateAktivitasPertanianUiEvent = updateAktivitasPertanianUiState.updateAktivitasPertanianUiEvent,
            formErrorStateUpdate = updateAktivitasPertanianUiState.formErrorState,
            onValueChange = onAktValueChange,
            modifier = Modifier.fillMaxWidth(),
            viewModel = viewModel
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
    formErrorStateUpdate: FormErrorStateUpdate,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateAktivitasPertanianUiEvent) -> Unit = {},
    enabled: Boolean = true,
    viewModel: UpdateAktivitasPertanianViewModel
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DynamicSelectTextField(
            selectedValue = updateAktivitasPertanianUiEvent.idTanaman,
            options = viewModel.tanamanList.map { it.namaTanaman },
            label = "Pilih Tanaman",
            onValueChangedEvent = { selectedNama ->
                onValueChange(updateAktivitasPertanianUiEvent.copy(idTanaman = selectedNama))
            },
            isError = formErrorStateUpdate.idTanaman != null,
            errorMessage = formErrorStateUpdate.idTanaman
        )

        DynamicSelectTextField(
            selectedValue = updateAktivitasPertanianUiEvent.idPekerja,
            options = viewModel.pekerjaList.map { it.namaPekerja },
            label = "Pilih Pekerja",
            onValueChangedEvent = { selectedNama ->
                onValueChange(updateAktivitasPertanianUiEvent.copy(idPekerja = selectedNama))
            },
            isError = formErrorStateUpdate.idPekerja != null,
            errorMessage = formErrorStateUpdate.idPekerja
        )

        OutlinedTextField(
            value = updateAktivitasPertanianUiEvent.tanggalAktivitas,
            onValueChange = { onValueChange(updateAktivitasPertanianUiEvent.copy(tanggalAktivitas = it)) },
            label = { Text("Tanggal Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorStateUpdate.tanggalAktivitas != null,
            supportingText = {
                formErrorStateUpdate.tanggalAktivitas?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = updateAktivitasPertanianUiEvent.deskripsiAktivitas,
            onValueChange = { onValueChange(updateAktivitasPertanianUiEvent.copy(deskripsiAktivitas = it)) },
            label = { Text("Deskripsi Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorStateUpdate.deskripsiAktivitas != null,
            supportingText = {
                formErrorStateUpdate.deskripsiAktivitas?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
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