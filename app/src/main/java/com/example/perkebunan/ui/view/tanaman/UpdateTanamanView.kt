package com.example.perkebunan.ui.view.tanaman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.tanaman.FormErrorStateUpdate
import com.example.perkebunan.ui.viewmodel.tanaman.UpdateTanamanUiEvent
import com.example.perkebunan.ui.viewmodel.tanaman.UpdateTanamanUiState
import com.example.perkebunan.ui.viewmodel.tanaman.UpdateTanamanViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiEditTanaman : DestinasiNavigasi {
    override val route = "tanaman_edit/{idTanaman}"
    override val titleRes = "Edit Tanaman"
    const val idTanamanArg = "idTanaman"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreenTanaman(
    navigateBack: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    idTanaman: String
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

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            viewModel.getTanamanbyIdTanaman(idTanaman)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEditTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        EditBodyTanaman(
            updateTanamanUiState = viewModel.uiState,
            onTnmValueChange = viewModel::updateTanamanUiState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFields()) {
                        viewModel.updateTnm(idTanaman)
                        delay(600)
                        withContext(Dispatchers.Main) {
                            onNavigateBack()
                        }
                    }
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
            formErrorStateUpdate = updateTanamanUiState.formErrorState,
            onValueChange = onTnmValueChange,
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
fun FormInputTanamanEdit(
    updateTanamanUiEvent: UpdateTanamanUiEvent,
    formErrorStateUpdate: FormErrorStateUpdate,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateTanamanUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateTanamanUiEvent.namaTanaman,
            onValueChange = { onValueChange(updateTanamanUiEvent.copy(namaTanaman = it)) },
            label = { Text("Nama Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorStateUpdate.namaTanaman != null,
            supportingText = {
                formErrorStateUpdate.namaTanaman?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        OutlinedTextField(
            value = updateTanamanUiEvent.periodeTanam,
            onValueChange = { onValueChange(updateTanamanUiEvent.copy(periodeTanam = it)) },
            label = { Text("Periode Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorStateUpdate.periodeTanam != null,
            supportingText = {
                formErrorStateUpdate.periodeTanam?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        OutlinedTextField(
            value = updateTanamanUiEvent.deskripsiTanaman,
            onValueChange = { onValueChange(updateTanamanUiEvent.copy(deskripsiTanaman = it)) },
            label = { Text("Deskripsi Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorStateUpdate.deskripsiTanaman != null,
            supportingText = {
                formErrorStateUpdate.deskripsiTanaman?.let {
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