package com.example.perkebunan.ui.view.tanaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.perkebunan.R
import com.example.perkebunan.model.Tanaman
import com.example.perkebunan.ui.viewmodel.tanaman.DetailTanamanUiState

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
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Nama Tanaman: ${tanaman.namaTanaman}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Periode Tanaman: ${tanaman.periodeTanam}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Deskripsi Tanaman: ${tanaman.deskripsiTanaman}",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}