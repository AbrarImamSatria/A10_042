package com.example.perkebunan.ui.view.aktivitaspertanian

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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perkebunan.R
import com.example.perkebunan.model.AktivitasPertanian
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.HomeAktivitasPertanianUiState
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.HomeAktivitasPertanianViewModel

object DestinasiHomeAktivitasPertanian: DestinasiNavigasi {
    override val route = "home Aktivitas Pertanian"
    override val titleRes = "Manajemen Aktivitas Pertanian"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAktivitasPertanianScreen(
    navigateToItemEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeAktivitasPertanianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedAktivitasPertanian by remember { mutableStateOf<AktivitasPertanian?>(null) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeAktivitasPertanian.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getAkt() },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            PemanisSectionAktivitasPertanian()
            DaftarAktivitasPertanianHeader(
                onTambahAktivitasPertanianClick = navigateToItemEntry
            )

            HomeAktivitasPertanianStatus(
                homeAktivitasPertanianUiState = viewModel.aktUiState,
                retryAction = { viewModel.getAkt() },
                onDetailClick = onDetailClick,
                onDeleteClick = { aktivitasPertanian ->
                    selectedAktivitasPertanian = aktivitasPertanian
                    showDeleteDialog = true
                }
            )
        }

        if (showDeleteDialog) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    selectedAktivitasPertanian?.let { aktivitasPertanian ->
                        viewModel.deleteAkt(aktivitasPertanian.idAktivitas.toString())
                        viewModel.getAkt()
                    }
                    showDeleteDialog = false
                },
                onDeleteCancel = {
                    showDeleteDialog = false
                }
            )
        }
    }
}

@Composable
fun PemanisSectionAktivitasPertanian(
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
                    painter = painterResource(id = R.drawable.agriculture),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 12.dp)
                )
                Text(
                    text = "Setiap benih yang ditanam,\nmembawa harapan baru.",
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
fun HomeAktivitasPertanianStatus(
    homeAktivitasPertanianUiState: HomeAktivitasPertanianUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (AktivitasPertanian) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    when (homeAktivitasPertanianUiState){
        is HomeAktivitasPertanianUiState.Loading -> OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is HomeAktivitasPertanianUiState.Succes ->
            if (homeAktivitasPertanianUiState.aktivitasPertanian.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Aktivitas Pertanian")
                }
            }else{
                AktLayout(
                    aktivitasPertanian = homeAktivitasPertanianUiState.aktivitasPertanian, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idAktivitas.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeAktivitasPertanianUiState.Error -> OnError(
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
fun DaftarAktivitasPertanianHeader(
    onTambahAktivitasPertanianClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Row untuk menyusun Teks "Daftar Aktivitas Pertanian" dan Tombol "Tambah Aktivitas Pertanian"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), //
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Daftar Aktivitas :",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
            )

            Button(
                onClick = onTambahAktivitasPertanianClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A67E)),
                modifier = Modifier
            ) {
                Text(
                    text = "Tambah Aktivitas",
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
                    text = "Cari Tanggal Aktivitas Pertanian Disini",
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
fun AktLayout(
    aktivitasPertanian: List<AktivitasPertanian>,
    modifier: Modifier = Modifier,
    onDetailClick: (AktivitasPertanian) -> Unit,
    onDeleteClick: (AktivitasPertanian) -> Unit = {}
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(aktivitasPertanian){ aktivitasPertanian ->
            AktCard(
                aktivitasPertanian = aktivitasPertanian,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(aktivitasPertanian) },
                onDeleteClick = {
                    onDeleteClick(aktivitasPertanian)
                }
            )
        }
    }
}

@Composable
fun AktCard(
    aktivitasPertanian: AktivitasPertanian,
    modifier: Modifier = Modifier,
    onDeleteClick: (AktivitasPertanian) -> Unit = {},
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
                painter = painterResource(id = R.drawable.chemical),
                contentDescription = "Gambar Aktivitas pertanian",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = aktivitasPertanian.tanggalAktivitas,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = aktivitasPertanian.deskripsiAktivitas,
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
                    contentDescription = "Hapus Catatan Panen",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onDeleteClick(aktivitasPertanian) }
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