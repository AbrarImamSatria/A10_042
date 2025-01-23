package com.example.perkebunan.ui.view.aktivitaspertanian

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.perkebunan.R
import com.example.perkebunan.model.AktivitasPertanian

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
                    painter = painterResource(id = R.drawable.chemical),
                    contentDescription = null,
                    modifier = Modifier
                        .size(112.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )

                DetailItem(label = "ID Panen :", value = aktivitasPertanian.idAktivitas)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "ID Panen :", value = aktivitasPertanian.idTanaman)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "ID Panen :", value = aktivitasPertanian.idPekerja)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "ID Panen :", value = aktivitasPertanian.tanggalAktivitas)
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                DetailItem(label = "ID Panen :", value = aktivitasPertanian.deskripsiAktivitas)
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
                text = "Edit Aktivitas Panen",
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