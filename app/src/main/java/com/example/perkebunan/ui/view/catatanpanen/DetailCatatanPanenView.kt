package com.example.perkebunan.ui.view.catatanpanen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perkebunan.R
import com.example.perkebunan.model.CatatanPanen
import com.example.perkebunan.navigation.DestinasiNavigasi
import com.example.perkebunan.ui.PenyediaViewModel
import com.example.perkebunan.ui.customwidget.CostumeTopAppBar
import com.example.perkebunan.ui.viewmodel.catatanpanen.DetailCatatanPanenUiState
import com.example.perkebunan.ui.viewmodel.catatanpanen.DetailCatatanPanenViewModel

object DestinasiDetailCatatanPanen : DestinasiNavigasi {
    override val route = "catatanpanen_detail/{idPanen}"
    override val titleRes = "Detail Catatan Panen"
    const val idCatatanPanenArg = "idPanen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenCatatanPanen(
    navigateBack: () -> Unit,
    navigateToEdit: (String) -> Unit,
    modifier: Modifier = Modifier,
    idPanen: String,
    viewModel: DetailCatatanPanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(idPanen) {
        viewModel.getCatatanPanenbyIdPanen(idPanen)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailCatatanPanen.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getCatatanPanenbyIdPanen(idPanen) },
            )
        },
    ) { innerPadding ->
        DetailCatatanPanenStatus(
            detailCatatanPanenUiState = viewModel.ctpnDetailUiState,
            retryAction = { viewModel.getCatatanPanenbyIdPanen(idPanen) },
            navigateToEdit = navigateToEdit,
            idPanen = idPanen,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun DetailCatatanPanenStatus(
    detailCatatanPanenUiState: DetailCatatanPanenUiState,
    retryAction: () -> Unit,
    navigateToEdit: (String) -> Unit,
    idPanen: String,
    modifier: Modifier = Modifier
) {
    when (detailCatatanPanenUiState) {
        is DetailCatatanPanenUiState.Loading -> OnLoadingDetail(
            modifier = modifier.fillMaxSize()
        )
        is DetailCatatanPanenUiState.Success -> DetailCatatanPanenLayout(
            catatanPanen = detailCatatanPanenUiState.catatanPanen,
            navigateToEdit = navigateToEdit,
            idPanen = idPanen,
            modifier = modifier.fillMaxWidth()
        )
        is DetailCatatanPanenUiState.Error -> OnErrorDetail(
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
fun DetailCatatanPanenLayout(
    catatanPanen: CatatanPanen,
    navigateToEdit: (String) -> Unit,
    idPanen: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
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
                    painter = painterResource(id = R.drawable.rambutan),
                    contentDescription = null,
                    modifier = Modifier
                        .size(112.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )

                DetailItem(label = "ID Panen :", value = catatanPanen.idPanen)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "ID Tanaman :", value = catatanPanen.idTanaman)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Tanggal Panen :", value = catatanPanen.tanggalPanen)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Jumlah Panen :", value = catatanPanen.jumlahPanen)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "Keterangan :", value = catatanPanen.keterangan)
            }
        }

        Button(
            onClick = { navigateToEdit(idPanen) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2CAC7C)
            )
        ) {
            Text(
                text = "Edit Catatan Panen",
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