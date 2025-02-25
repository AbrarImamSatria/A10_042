package com.example.perkebunan.ui.view.aktivitaspertanian

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.perkebunan.model.AktivitasPertanian
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.DetailAktivitasPertanianUiState
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.DetailAktivitasPertanianViewModel

object DestinasiDetailAktivitasPertanian : DestinasiNavigasi {
    override val route = "aktivitaspertanian_detail/{idAktivitas}"
    override val titleRes = "Detail Aktivitas Pertanian"
    const val idAktivitasPertanianArg = "idAktivitas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenAktivitasPertanian(
    navigateBack: () -> Unit,
    navigateToEdit: (String) -> Unit,
    modifier: Modifier = Modifier,
    idAktivitas: String,
    viewModel: DetailAktivitasPertanianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(idAktivitas) {
        viewModel.getAktivitasPertanianbyIdAktivitas(idAktivitas)
    }


    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailAktivitasPertanian.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getAktivitasPertanianbyIdAktivitas(idAktivitas) },
            )
        },
    ) { innerPadding ->
        DetailAktivitasPertanianStatus(
            detailAktivitasPertanianUiState = viewModel.aktDetailUiState,
            retryAction = { viewModel.getAktivitasPertanianbyIdAktivitas(idAktivitas) },
            navigateToEdit = navigateToEdit,
            idAktivitas = idAktivitas,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun DetailAktivitasPertanianStatus(
    detailAktivitasPertanianUiState: DetailAktivitasPertanianUiState,
    retryAction: () -> Unit,
    navigateToEdit: (String) -> Unit,
    idAktivitas: String,
    modifier: Modifier = Modifier
) {
    when (detailAktivitasPertanianUiState) {
        is DetailAktivitasPertanianUiState.Loading -> OnLoadingDetail(
            modifier = modifier.fillMaxSize()
        )
        is DetailAktivitasPertanianUiState.Success -> DetailAktivitasPertanianLayout(
            aktivitasPertanian = detailAktivitasPertanianUiState.aktivitasPertanian,
            navigateToEdit = navigateToEdit,
            idAktivitas = idAktivitas,
            modifier = modifier.fillMaxWidth()
        )
        is DetailAktivitasPertanianUiState.Error -> OnErrorDetail(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
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
fun DetailAktivitasPertanianLayout(
    aktivitasPertanian: AktivitasPertanian,
    navigateToEdit: (String) -> Unit,
    idAktivitas: String,
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
                    painter = painterResource(id = R.drawable.chemical),
                    contentDescription = null,
                    modifier = Modifier
                        .size(112.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )

                DetailItem(label = "ID Aktivitas :", value = aktivitasPertanian.idAktivitas.toString())
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Nama Tanaman :", value = aktivitasPertanian.idTanaman)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Nama Pekerja :", value = aktivitasPertanian.idPekerja)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Tanggal Aktivitas :", value = aktivitasPertanian.tanggalAktivitas)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Deskripsi Aktivitas :", value = aktivitasPertanian.deskripsiAktivitas)
            }
        }

        Button(
            onClick = { navigateToEdit(idAktivitas) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2CAC7C)
            )
        ) {
            Text(
                text = "Edit Aktivitas Pertanian",
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