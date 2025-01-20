package com.example.perkebunan.ui.view.tanaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    override val route = "detail/{idTanaman}"
    override val titleRes = "Detail Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTanaman(
    navigateBack: () -> Unit,
    navigateToEdit: (String) -> Unit,
    modifier: Modifier = Modifier,
    idTanaman: String,
    viewModel: DetailTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(idTanaman) {
        viewModel.getTanamanbyIdTanaman(idTanaman)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getTanamanbyIdTanaman(idTanaman) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEdit(idTanaman) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa"
                )
            }
        }
    ) { innerPadding ->
        DetailTanamanStatus(
            detailTanamanUiState = viewModel.tnmDetailUiState,
            retryAction = { viewModel.getTanamanbyIdTanaman(idTanaman) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun DetailTanamanStatus(
    detailTanamanUiState: DetailTanamanUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailTanamanUiState) {
        is DetailTanamanUiState.Loading -> OnLoadingDetail(modifier = modifier.fillMaxSize())
        is DetailTanamanUiState.Success -> DetailTanamanLayout(
            tanaman = detailTanamanUiState.tanaman,
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
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "ID Tanaman: ${tanaman.idTanaman}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Nama Tanaman: ${tanaman.namaTanaman}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Periode Tanaman: ${tanaman.periodeTanam}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Deskripsi Tanaman: ${tanaman.deskripsiTanaman}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}