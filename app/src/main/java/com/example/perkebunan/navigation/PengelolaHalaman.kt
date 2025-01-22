package com.example.perkebunan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.perkebunan.ui.view.pekerja.DestinasiDetailPekerja
import com.example.perkebunan.ui.view.pekerja.DestinasiEditPekerja
import com.example.perkebunan.ui.view.pekerja.DestinasiEntryPekerja
import com.example.perkebunan.ui.view.pekerja.DestinasiHomePekerja
import com.example.perkebunan.ui.view.pekerja.DetailScreenPekerja
import com.example.perkebunan.ui.view.pekerja.EditScreenPekerja
import com.example.perkebunan.ui.view.pekerja.EntryPkjScreen
import com.example.perkebunan.ui.view.pekerja.HomePekerjaScreen
import com.example.perkebunan.ui.view.tanaman.DestinasiDetailTanaman
import com.example.perkebunan.ui.view.tanaman.DestinasiEditTanaman
import com.example.perkebunan.ui.view.tanaman.DestinasiEntryTanaman
import com.example.perkebunan.ui.view.tanaman.DestinasiHomeTanaman
import com.example.perkebunan.ui.view.tanaman.DetailScreenTanaman
import com.example.perkebunan.ui.view.tanaman.EditScreenTanaman
import com.example.perkebunan.ui.view.tanaman.EntryTnmScreen
import com.example.perkebunan.ui.view.tanaman.HomeTanamanScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeTanaman.route,
        modifier = Modifier
    ) {
        // RUTE UNTUK TANAMAN
        composable(DestinasiHomeTanaman.route) {
            HomeTanamanScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryTanaman.route) },
                onDetailClick = { idTanaman ->
                    navController.navigate("tanaman_detail/$idTanaman")
                },
                navigateToPekerja = { navController.navigate(DestinasiHomePekerja.route) }
            )
        }

        composable(DestinasiEntryTanaman.route) {
            EntryTnmScreen(navigateBack = {
                navController.navigate(DestinasiHomeTanaman.route) {
                    popUpTo(DestinasiHomeTanaman.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = DestinasiDetailTanaman.route,
            arguments = listOf(navArgument(DestinasiDetailTanaman.idTanamanArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idTanaman = backStackEntry.arguments?.getString(DestinasiDetailTanaman.idTanamanArg) ?: ""
            DetailScreenTanaman(
                idTanaman = idTanaman,
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { id ->
                    navController.navigate("tanaman_edit/$id")
                }
            )
        }

        composable(
            route = DestinasiEditTanaman.route,
            arguments = listOf(navArgument(DestinasiEditTanaman.idTanamanArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idTanaman = backStackEntry.arguments?.getString(DestinasiEditTanaman.idTanamanArg)
            EditScreenTanaman(
                idTanaman = idTanaman ?: "",
                navigateBack = { navController.navigateUp() },
                onNavigateBack = {
                    navController.navigate("tanaman_detail/$idTanaman") {
                        popUpTo(DestinasiEditTanaman.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        //RUTE UNTUK PEKERJA

        composable(DestinasiHomePekerja.route) {
            HomePekerjaScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPekerja.route) },
                navigateBack = { navController.navigateUp() },
                onDetailClick = { idPekerja ->
                    navController.navigate("pekerja_detail/$idPekerja")
                },
            )
        }

        composable(DestinasiEntryPekerja.route) {
            EntryPkjScreen(navigateBack = {
                navController.navigate(DestinasiHomePekerja.route) {
                    popUpTo(DestinasiHomePekerja.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = DestinasiDetailPekerja.route,
            arguments = listOf(navArgument(DestinasiDetailPekerja.idPekerjaArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idPekerja = backStackEntry.arguments?.getString(DestinasiDetailPekerja.idPekerjaArg) ?: ""
            DetailScreenPekerja(
                idPekerja = idPekerja,
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { id ->
                    navController.navigate("pekerja_edit/$id")
                }
            )
        }

        composable(
            route = DestinasiEditPekerja.route,
            arguments = listOf(navArgument(DestinasiEditPekerja.idPekerjaArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idPekerja = backStackEntry.arguments?.getString(DestinasiEditPekerja.idPekerjaArg)
            EditScreenPekerja(
                idPekerja = idPekerja ?: "",
                navigateBack = { navController.navigateUp() },
                onNavigateBack = {
                    navController.navigate("Pekerja_detail/$idPekerja") {
                        popUpTo(DestinasiEditPekerja.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}