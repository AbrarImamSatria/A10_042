package com.example.perkebunan.ui.view.tanaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perkebunan.R
import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.tanaman.DetailTanamanUiState
import com.example.perkebunan.ui.viewmodel.tanaman.DetailTanamanViewModel

object DestinasiDetailTanaman : DestinasiNavigasi {
    override val route = "tanaman_detail/{idTanaman}"
    override val titleRes = "Detail Tanaman"
    const val idTanamanArg = "idTanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTanaman(
    navigateBack: () -> Unit,
    navigateToEdit: (String) -> Unit,
    navigateToCatatanPanen: (String) -> Unit,
    modifier: Modifier = Modifier,
    idTanaman: String,
    viewModel: DetailTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(idTanaman) {
        viewModel.getTanamanbyIdTanaman(idTanaman)
    }


    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTanaman.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getTanamanbyIdTanaman(idTanaman) },
            )
        },
        floatingActionButton = {
            Box(modifier = Modifier.padding(bottom = 24.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(
                        text = "Tambah Panen",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    FloatingActionButton(
                        onClick = { navigateToCatatanPanen(idTanaman) },
                        containerColor = Color(0xFF2CAC7C),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        DetailTanamanStatus(
            detailTanamanUiState = viewModel.tnmDetailUiState,
            retryAction = { viewModel.getTanamanbyIdTanaman(idTanaman) },
            navigateToEdit = navigateToEdit,
            idTanaman = idTanaman,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun DetailTanamanStatus(
    detailTanamanUiState: DetailTanamanUiState,
    retryAction: () -> Unit,
    navigateToEdit: (String) -> Unit,
    idTanaman: String,
    modifier: Modifier = Modifier
) {
    when (detailTanamanUiState) {
        is DetailTanamanUiState.Loading -> OnLoadingDetail(modifier = modifier.fillMaxSize())
        is DetailTanamanUiState.Success -> DetailTanamanLayout(
            tanaman = detailTanamanUiState.tanaman,
            navigateToEdit = navigateToEdit,
            idTanaman = idTanaman,
            modifier = modifier.fillMaxWidth()
        )
        is DetailTanamanUiState.Error -> OnErrorDetail(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
private fun OnErrorDetail(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.eror),
            contentDescription = stringResource(R.string.loading_failed)
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
private fun OnLoadingDetail(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun DetailTanamanLayout(
    tanaman: Tanaman,
    navigateToEdit: (String) -> Unit,
    idTanaman: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.palmoil),
                    contentDescription = null,
                    modifier = Modifier
                        .size(112.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )

                DetailItem(label = "ID Tanaman :", value = tanaman.idTanaman.toString())
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Nama Tanaman :", value = tanaman.namaTanaman)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Periode Tanam :", value = tanaman.periodeTanam)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Deskripsi :", value = tanaman.deskripsiTanaman)
            }
        }

        Button(
            onClick = { navigateToEdit(idTanaman) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2CAC7C)
            )
        ) {
            Text(
                text = "Edit Tanaman",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun DetailItem(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
