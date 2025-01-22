package com.example.perkebunan.ui

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.perkebunan.PerkebunanApplications
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
        initializer { HomeTanamanViewModel(aplikasiPerkebunan().container.tanamanRepository) }
        initializer { InsertTanamanViewModel(aplikasiPerkebunan().container.tanamanRepository) }
        initializer { DetailTanamanViewModel(aplikasiPerkebunan().container.tanamanRepository) }
        initializer { UpdateTanamanViewModel(aplikasiPerkebunan().container.tanamanRepository) }
        initializer { HomePekerjaViewModel(aplikasiPerkebunan().container.pekerjaRepository) }
        initializer { InsertPekerjaViewModel(aplikasiPerkebunan().container.pekerjaRepository) }
        initializer { DetailPekerjaViewModel(aplikasiPerkebunan().container.pekerjaRepository) }
        initializer { UpdatePekerjaViewModel(aplikasiPerkebunan().container.pekerjaRepository) }
    }

    fun CreationExtras.aplikasiPerkebunan():PerkebunanApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PerkebunanApplications)
}