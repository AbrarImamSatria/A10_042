package com.example.perkebunan.ui.view.tanaman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.perkebunan.model.Tanaman

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