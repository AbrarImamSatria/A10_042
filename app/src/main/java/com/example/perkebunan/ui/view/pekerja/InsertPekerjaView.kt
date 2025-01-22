package com.example.perkebunan.ui.view.pekerja

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.pekerja.InsertPekerjaUiEvent
import com.example.perkebunan.ui.viewmodel.pekerja.InsertPekerjaUiState
import com.example.perkebunan.ui.viewmodel.pekerja.InsertPekerjaViewModel
import com.example.perkebunan.ui.viewmodel.tanaman.InsertTanamanViewModel
import kotlinx.coroutines.launch


object DestinasiEntryPekerja: DestinasiNavigasi {
    override val route = "item_entryPekerja"
    override val titleRes = "Entry Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPkjScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPekerja.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertPekerjaUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertPkjState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPkj()
                    navigateBack()
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
    insertPekerjaUiState: InsertPekerjaUiState,
    onSiswaValueChange: (InsertPekerjaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertPekerjaUiEvent = insertPekerjaUiState.insertPekerjaUiEvent,
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
    insertPekerjaUiEvent: InsertPekerjaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPekerjaUiEvent)->Unit = {},
    enabled: Boolean = true
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertPekerjaUiEvent.idPekerja,
            onValueChange = {onValueChange(insertPekerjaUiEvent.copy(idPekerja = it))},
            label = { Text("ID Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPekerjaUiEvent.namaPekerja,
            onValueChange = {onValueChange(insertPekerjaUiEvent.copy(namaPekerja = it))},
            label = { Text("Nama Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPekerjaUiEvent.jabatan,
            onValueChange = {onValueChange(insertPekerjaUiEvent.copy(jabatan = it))},
            label = { Text("Jabatan Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPekerjaUiEvent.kontakPekerja,
            onValueChange = {onValueChange(insertPekerjaUiEvent.copy(kontakPekerja = it))},
            label = { Text("Kontak Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
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