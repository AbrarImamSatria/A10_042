package com.example.perkebunan.ui.view.pekerja

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
import com.example.perkebunan.model.Pekerja
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.pekerja.DetailPekerjaUiState
import com.example.perkebunan.ui.viewmodel.pekerja.DetailPekerjaViewModel

object DestinasiDetailPekerja : DestinasiNavigasi {
    override val route = "pekerja_detail/{idPekerja}"
    override val titleRes = "Detail Pekerja"
    const val idPekerjaArg = "idPekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenPekerja(
    navigateBack: () -> Unit,
    navigateToEdit: (String) -> Unit,
    modifier: Modifier = Modifier,
    idPekerja: String,
    viewModel: DetailPekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(idPekerja) {
        viewModel.getPekerjabyIdPekerja(idPekerja)
    }


    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPekerja.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getPekerjabyIdPekerja(idPekerja) },
            )
        },
    ) { innerPadding ->
        DetailPekerjaStatus(
            detailPekerjaUiState = viewModel.pkjDetailUiState,
            retryAction = { viewModel.getPekerjabyIdPekerja(idPekerja) },
            navigateToEdit = navigateToEdit,
            idPekerja = idPekerja,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun DetailPekerjaStatus(
    detailPekerjaUiState: DetailPekerjaUiState,
    retryAction: () -> Unit,
    navigateToEdit: (String) -> Unit,
    idPekerja: String,
    modifier: Modifier = Modifier
) {
    when (detailPekerjaUiState) {
        is DetailPekerjaUiState.Loading -> OnLoadingDetail(
            modifier = modifier.fillMaxSize()
        )
        is DetailPekerjaUiState.Success -> DetailPekerjaLayout(
            pekerja = detailPekerjaUiState.pekerja,
            navigateToEdit = navigateToEdit,
            idPekerja = idPekerja,
            modifier = modifier.fillMaxWidth()
        )
        is DetailPekerjaUiState.Error -> OnErrorDetail(
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
fun DetailPekerjaLayout(
    pekerja: Pekerja,
    navigateToEdit: (String) -> Unit,
    idPekerja: String,
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
                    painter = painterResource(id = R.drawable.farmer2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(112.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )

                DetailItem(label = "ID Pekerja :", value = pekerja.idPekerja.toString())
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Nama Pekerja :", value = pekerja.namaPekerja)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Jabatan Pekerja :", value = pekerja.jabatan)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Kontak Pekerja :", value = pekerja.kontakPekerja)
            }
        }

        Button(
            onClick = { navigateToEdit(idPekerja) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2CAC7C)
            )
        ) {
            Text(
                text = "Edit Pekerja",
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