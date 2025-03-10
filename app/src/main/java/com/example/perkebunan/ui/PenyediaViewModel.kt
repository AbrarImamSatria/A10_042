package com.example.perkebunan.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.perkebunan.PerkebunanApplications
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.DetailAktivitasPertanianViewModel
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.HomeAktivitasPertanianViewModel
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.InsertAktivitasPertanianViewModel
import com.example.perkebunan.ui.viewmodel.aktivitaspertanian.UpdateAktivitasPertanianViewModel
import com.example.perkebunan.ui.viewmodel.catatanpanen.DetailCatatanPanenViewModel
import com.example.perkebunan.ui.viewmodel.catatanpanen.HomeCatatanPanenViewModel
import com.example.perkebunan.ui.viewmodel.catatanpanen.InsertCatatanPanenViewModel
import com.example.perkebunan.ui.viewmodel.catatanpanen.UpdateCatatanPanenViewModel
import com.example.perkebunan.ui.viewmodel.pekerja.DetailPekerjaViewModel
import com.example.perkebunan.ui.viewmodel.pekerja.HomePekerjaViewModel
import com.example.perkebunan.ui.viewmodel.pekerja.InsertPekerjaViewModel
import com.example.perkebunan.ui.viewmodel.pekerja.UpdatePekerjaViewModel
import com.example.perkebunan.ui.viewmodel.tanaman.DetailTanamanViewModel
import com.example.perkebunan.ui.viewmodel.tanaman.HomeTanamanViewModel
import com.example.perkebunan.ui.viewmodel.tanaman.InsertTanamanViewModel
import com.example.perkebunan.ui.viewmodel.tanaman.UpdateTanamanViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        // VIEWMODEL TANAMAN
        initializer { HomeTanamanViewModel(aplikasiPerkebunan().container.tanamanRepository) }
        initializer { InsertTanamanViewModel(aplikasiPerkebunan().container.tanamanRepository) }
        initializer { DetailTanamanViewModel(aplikasiPerkebunan().container.tanamanRepository) }
        initializer { UpdateTanamanViewModel(aplikasiPerkebunan().container.tanamanRepository) }

        // VIEWMODEL PEKERJA
        initializer { HomePekerjaViewModel(aplikasiPerkebunan().container.pekerjaRepository) }
        initializer { InsertPekerjaViewModel(aplikasiPerkebunan().container.pekerjaRepository) }
        initializer { DetailPekerjaViewModel(aplikasiPerkebunan().container.pekerjaRepository) }
        initializer { UpdatePekerjaViewModel(aplikasiPerkebunan().container.pekerjaRepository) }

        // VIEWMODEL CATATAN PANEN
        initializer { HomeCatatanPanenViewModel(aplikasiPerkebunan().container.catatanPanenRepository) }
        initializer {
            InsertCatatanPanenViewModel(
                aplikasiPerkebunan().container.catatanPanenRepository,
                aplikasiPerkebunan().container.tanamanRepository
            )
        }
        initializer { DetailCatatanPanenViewModel(aplikasiPerkebunan().container.catatanPanenRepository) }
        initializer {
            UpdateCatatanPanenViewModel(
                aplikasiPerkebunan().container.catatanPanenRepository,
                aplikasiPerkebunan().container.tanamanRepository
            )
        }

        // VIEWMODEL AKTIVITAS PERTANIAN
        initializer { HomeAktivitasPertanianViewModel(aplikasiPerkebunan().container.aktivitasPertanianRepository) }
        initializer {
            InsertAktivitasPertanianViewModel(
                aplikasiPerkebunan().container.aktivitasPertanianRepository,
                aplikasiPerkebunan().container.tanamanRepository,
                aplikasiPerkebunan().container.pekerjaRepository
            )
        }
        initializer { DetailAktivitasPertanianViewModel(aplikasiPerkebunan().container.aktivitasPertanianRepository) }
        initializer {
            UpdateAktivitasPertanianViewModel(
                aplikasiPerkebunan().container.aktivitasPertanianRepository,
                aplikasiPerkebunan().container.tanamanRepository,
                aplikasiPerkebunan().container.pekerjaRepository
            )
        }
    }

    fun CreationExtras.aplikasiPerkebunan():PerkebunanApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PerkebunanApplications)
}