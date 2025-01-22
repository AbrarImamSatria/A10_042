package com.example.perkebunan.ui.view.tanaman

import CustomTopBar
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perkebunan.R
import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.tanaman.HomeTanamanUiState
import com.example.perkebunan.ui.viewmodel.tanaman.HomeTanamanViewModel

object DestinasiHomeTanaman: DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTanamanScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedTanaman by remember { mutableStateOf<Tanaman?>(null) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                onNotificationClick = { /* Handle notification click */ },
                onProfileClick = { /* Handle profile click */ }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            FiturLainnyaSection(
                navigateToPekerja = { /* Navigasi ke halaman pekerja */ },
                navigateToCatatan = { /* Navigasi ke halaman catatan */ },
                navigateToAktivitas = { /* Navigasi ke halaman aktivitas */ },
                onRefresh = { viewModel.getTnm() },
            )
            PemanisSection()

            DaftarTanamanHeader(
                onTambahTanamanClick = navigateToItemEntry
            )

            HomeTanamanStatus(
                homeTanamanUiState = viewModel.tnmUiState,
                retryAction = { viewModel.getTnm() },
                onDetailClick = onDetailClick,
                onDeleteClick = { tanaman ->
                    selectedTanaman = tanaman
                    showDeleteDialog = true
                }
            )
        }

        if (showDeleteDialog) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    selectedTanaman?.let { tanaman ->
                        viewModel.deleteTnm(tanaman.idTanaman)
                        viewModel.getTnm()
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
fun FiturLainnyaSection(
    navigateToPekerja: () -> Unit,
    navigateToCatatan: () -> Unit,
    navigateToAktivitas: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Fitur Lainnya:",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onRefresh) {
                Icon(
                    painter = painterResource(id = R.drawable.refresh),
                    contentDescription = "Refresh",
                    modifier = Modifier.size(27.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FiturCard(
                iconResId = R.drawable.farmer,
                title = "Pekerja",
                onClick = navigateToPekerja
            )
            FiturCard(
                iconResId = R.drawable.essay,
                title = "Catatan",
                onClick = navigateToCatatan
            )
            FiturCard(
                iconResId = R.drawable.planting,
                title = "Aktivitas",
                onClick = navigateToAktivitas
            )
        }
    }
}


@Composable
fun FiturCard(
    iconResId: Int,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(90.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF00A67E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = title,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
    }
}

@Composable
fun PemanisSection(
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
                    painter = painterResource(id = R.drawable.bumi),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 12.dp)
                )
                Text(
                    text = "Merawat dengan Hati,\nMenumbuhkan Harmoni.",
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
fun HomeTanamanStatus(
    homeTanamanUiState: HomeTanamanUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tanaman) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    when (homeTanamanUiState){
        is HomeTanamanUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTanamanUiState.Succes ->
            if (homeTanamanUiState.tanaman.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Tanaman")
                }
            }else{
                TnmLayout(

                    tanaman = homeTanamanUiState.tanaman, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idTanaman)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeTanamanUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun DaftarTanamanHeader(
    onTambahTanamanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Row untuk menyusun Teks "Daftar Tanaman" dan Tombol "Tambah Tanaman"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), //
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Daftar Tanaman:",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
            )

            Button(
                onClick = onTambahTanamanClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A67E)),
                modifier = Modifier
            ) {
                Text(
                    text = "Tambah Tanaman",
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
                    text = "Cari Tanaman Anda Disini",
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
fun TnmLayout(
    tanaman: List<Tanaman>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tanaman) -> Unit,
    onDeleteClick: (Tanaman) -> Unit = {}
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(tanaman){ tanaman ->
            TnmCard(
                tanaman = tanaman,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tanaman) },
                onDeleteClick = {
                    onDeleteClick(tanaman)
                }
            )
        }
    }
}

@Composable
fun TnmCard(
    tanaman: Tanaman,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tanaman) -> Unit = {},
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
                painter = painterResource(id = R.drawable.palmoil),
                contentDescription = "Gambar Tanaman",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ID ${tanaman.idTanaman}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = tanaman.namaTanaman,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                )
                Text(
                    text = tanaman.periodeTanam,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
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
                    contentDescription = "Hapus Tanaman",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onDeleteClick(tanaman) }
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