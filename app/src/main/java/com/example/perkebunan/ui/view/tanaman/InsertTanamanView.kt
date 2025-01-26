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
import com.example.perkebunan.ui.viewmodel.tanaman.FormErrorState
import com.example.perkebunan.ui.viewmodel.tanaman.InsertTanamanUiEvent
import com.example.perkebunan.ui.viewmodel.tanaman.InsertTanamanUiState
import com.example.perkebunan.ui.viewmodel.tanaman.InsertTanamanViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiEntryTanaman: DestinasiNavigasi {
    override val route = "item_entryTanaman"
    override val titleRes = "Entry Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTnmScreen(
    navigateBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                title = DestinasiEntryTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){ innerPadding ->
        EntryBody(
            insertTanamanUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertTnmState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFields()) {
                        viewModel.insertTnm()
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
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertTanamanUiState: InsertTanamanUiState,
    onSiswaValueChange: (InsertTanamanUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertTanamanUiEvent = insertTanamanUiState.insertTanamanUiEvent,
            formErrorState = insertTanamanUiState.formErrorState,
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
    insertTanamanUiEvent: InsertTanamanUiEvent,
    formErrorState: FormErrorState,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTanamanUiEvent)->Unit = {},
    enabled: Boolean = true
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertTanamanUiEvent.namaTanaman,
            onValueChange = {onValueChange(insertTanamanUiEvent.copy(namaTanaman = it))},
            label = { Text("Nama Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorState.namaTanaman != null,
            supportingText = {
                formErrorState.namaTanaman?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        OutlinedTextField(
            value = insertTanamanUiEvent.periodeTanam,
            onValueChange = {onValueChange(insertTanamanUiEvent.copy(periodeTanam = it))},
            label = { Text("Periode Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorState.periodeTanam != null,
            supportingText = {
                formErrorState.periodeTanam?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        OutlinedTextField(
            value = insertTanamanUiEvent.deskripsiTanaman,
            onValueChange = {onValueChange(insertTanamanUiEvent.copy(deskripsiTanaman = it))},
            label = { Text("Deskripsi Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = formErrorState.deskripsiTanaman != null,
            supportingText = {
                formErrorState.deskripsiTanaman?.let {
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