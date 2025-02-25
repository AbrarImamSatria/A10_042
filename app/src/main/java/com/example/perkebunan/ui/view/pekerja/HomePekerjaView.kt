package com.example.perkebunan.ui.view.pekerja

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perkebunan.R
import com.example.perkebunan.model.Pekerja
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.pekerja.HomePekerjaUiState
import com.example.perkebunan.ui.viewmodel.pekerja.HomePekerjaViewModel

object DestinasiHomePekerja: DestinasiNavigasi {
    override val route = "home pekerja"
    override val titleRes = "Manajemen Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePekerjaScreen(
    navigateToItemEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomePekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedPekerja by remember { mutableStateOf<Pekerja?>(null) }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePekerja.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getPkj() },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item { PemanisSectionPekerja() }

            item {
                DaftarPekerjaHeader(
                    onTambahPekerjaClick = navigateToItemEntry
                )
            }

            when (val state = viewModel.pkjUiState) {
                is HomePekerjaUiState.Loading -> {
                    item { OnLoading() }
                }
                is HomePekerjaUiState.Error -> {
                    item {
                        OnError(
                            retryAction = { viewModel.getPkj() }
                        )
                    }
                }
                is HomePekerjaUiState.Succes -> {
                    if (state.pekerja.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Tidak ada data Pekerja")
                            }
                        }
                    } else {
                        items(state.pekerja) { pekerja ->
                            PkjCard(
                                pekerja = pekerja,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .clickable { onDetailClick(pekerja.idPekerja.toString()) },
                                onDeleteClick = {
                                    selectedPekerja = it
                                    showDeleteDialog = true
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }

        if (showDeleteDialog) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    selectedPekerja?.let { pekerja ->
                        viewModel.deletePkj(pekerja.idPekerja.toString())
                        viewModel.getPkj()
                    }
                    showDeleteDialog = false
                },
                onDeleteCancel = { showDeleteDialog = false }
            )
        }
    }
}

@Composable
fun PemanisSectionPekerja(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF00A67E), shape = MaterialTheme.shapes.medium)
                .padding(vertical = 10.dp, horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.partners),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 12.dp)
                )
                Text(
                    text = "Bekerja dengan Semangat,\nMenuju Sukses Bersama.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
}

@Composable
fun HomePekerjaStatus(
    homePekerjaUiState: HomePekerjaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    when (homePekerjaUiState){
        is HomePekerjaUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomePekerjaUiState.Succes ->
            if (homePekerjaUiState.pekerja.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Pekerja")
                }
            }else{
                PkjLayout(

                    pekerja = homePekerjaUiState.pekerja, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idPekerja.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomePekerjaUiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

/**
 * The home sceen displaying the loading message.
 */

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

/**
 * The home sceen displaying error mssage with re-attempt button.
 */

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.eror), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun DaftarPekerjaHeader(
    onTambahPekerjaClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Row untuk menyusun Teks "Daftar Pekerja" dan Tombol "Tambah Pekerja"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Daftar Pekerja:",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
            )

            Button(
                onClick = onTambahPekerjaClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A67E)),
                modifier = Modifier
            ) {
                Text(
                    text = "Tambah Pekerja",
                    color = Color.White
                )
            }
        }

        Box(
            modifier = Modifier
                .width(400.dp)
                .height(50.dp)
                .padding(start = 10.dp)
                .padding(top = 10.dp)
                .background(Color(0xFF00A67E), shape = MaterialTheme.shapes.medium),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Cari Nama Pekerja Disini",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun PkjLayout(
    pekerja: List<Pekerja>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pekerja) -> Unit,
    onDeleteClick: (Pekerja) -> Unit = {}
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(pekerja){ pekerja ->
            PkjCard(
                pekerja = pekerja,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pekerja) },
                onDeleteClick = {
                    onDeleteClick(pekerja)
                }
            )
        }
    }
}

@Composable
fun PkjCard(
    pekerja: Pekerja,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit = {},
){
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.farmer2),
                contentDescription = "Gambar Pekerja",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = pekerja.namaPekerja,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = pekerja.jabatan,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray,fontWeight = FontWeight.Bold)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "Info Detail",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {}
                )
                Image(
                    painter = painterResource(id = R.drawable.bin),
                    contentDescription = "Hapus Pekerja",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onDeleteClick(pekerja) }
                )
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {/*Do Nothing*/},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        })
}