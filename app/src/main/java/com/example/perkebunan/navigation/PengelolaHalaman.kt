package com.example.perkebunan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.perkebunan.ui.view.aktivitaspertanian.DestinasiDetailAktivitasPertanian
import com.example.perkebunan.ui.view.aktivitaspertanian.DestinasiEditAktivitasPertanian
import com.example.perkebunan.ui.view.aktivitaspertanian.DestinasiEntryAktivitasPertanian
import com.example.perkebunan.ui.view.aktivitaspertanian.DestinasiHomeAktivitasPertanian
import com.example.perkebunan.ui.view.aktivitaspertanian.DetailScreenAktivitasPertanian
import com.example.perkebunan.ui.view.aktivitaspertanian.EditScreenAktivitasPertanian
import com.example.perkebunan.ui.view.aktivitaspertanian.EntryAktScreen
import com.example.perkebunan.ui.view.aktivitaspertanian.HomeAktivitasPertanianScreen
import com.example.perkebunan.ui.view.catatanpanen.DestinasiDetailCatatanPanen
import com.example.perkebunan.ui.view.catatanpanen.DestinasiEditCatatanPanen
import com.example.perkebunan.ui.view.catatanpanen.DestinasiEntryCatatanPanen
import com.example.perkebunan.ui.view.catatanpanen.DestinasiHomeCatatanPanen
import com.example.perkebunan.ui.view.catatanpanen.DetailScreenCatatanPanen
import com.example.perkebunan.ui.view.catatanpanen.EditScreenCatatanPanen
import com.example.perkebunan.ui.view.catatanpanen.EntryCtpnScreen
import com.example.perkebunan.ui.view.catatanpanen.HomeCatatanPanenScreen
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
import com.example.perkebunan.ui.view.tanaman.DestinasiWelcomeScreen
import com.example.perkebunan.ui.view.tanaman.DetailScreenTanaman
import com.example.perkebunan.ui.view.tanaman.EditScreenTanaman
import com.example.perkebunan.ui.view.tanaman.EntryTnmScreen
import com.example.perkebunan.ui.view.tanaman.HomeTanamanScreen
import com.example.perkebunan.ui.view.tanaman.WelcomeScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiWelcomeScreen.route,
        modifier = Modifier
    ) {

        composable(DestinasiWelcomeScreen.route) {
            WelcomeScreen(navController = navController)
        }

        // RUTE UNTUK TANAMAN
        composable(DestinasiHomeTanaman.route) {
            HomeTanamanScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryTanaman.route) },
                onDetailClick = { idTanaman ->
                    navController.navigate("tanaman_detail/$idTanaman")
                },
                navigateToPekerja = { navController.navigate(DestinasiHomePekerja.route) },
                navigateToCatatanPanen = {navController.navigate(DestinasiHomeCatatanPanen.route)},
                navigateToAktivitasPertanian = {navController.navigate(DestinasiHomeAktivitasPertanian.route)}
            )
        }

        composable(DestinasiEntryTanaman.route) {
            EntryTnmScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeTanaman.route) {
                        popUpTo(DestinasiHomeTanaman.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigate = {
                    navController.navigate(DestinasiHomeTanaman.route) {
                        popUpTo(DestinasiHomeTanaman.route) {
                            inclusive = true
                        }
                    }
                }
            )
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
                },
                navigateToCatatanPanen = { id ->
                    navController.navigate("${DestinasiEntryCatatanPanen.route}/$id")
                }
            )
        }

        composable(
            route = "${DestinasiEntryCatatanPanen.route}/{idTanaman}",
            arguments = listOf(navArgument("idTanaman") { type = NavType.StringType })
        ) { backStackEntry ->
            val idTanaman = backStackEntry.arguments?.getString("idTanaman") ?: ""
            EntryCtpnScreen(
                navigateBack = {
                    navController.navigate("tanaman_detail/$idTanaman") {
                        popUpTo("tanaman_detail/$idTanaman") {
                            inclusive = true
                        }
                    }
                },
                onNavigate = {
                    navController.navigate("tanaman_detail/$idTanaman") {
                        popUpTo("tanaman_detail/$idTanaman") {
                            inclusive = true
                        }
                    }
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
            EntryPkjScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePekerja.route) {
                        popUpTo(DestinasiHomePekerja.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigate = {
                    navController.navigate(DestinasiHomePekerja.route) {
                        popUpTo(DestinasiHomePekerja.route) {
                            inclusive = true
                        }
                    }
                }
            )
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

        //RUTE UNTUK CATATAN PANEN
        composable(DestinasiHomeCatatanPanen.route) {
            HomeCatatanPanenScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryCatatanPanen.route) },
                navigateBack = { navController.navigateUp() },
                onDetailClick = { idPanen ->
                    navController.navigate("catatanpanen_detail/$idPanen")
                },
            )
        }

        composable(DestinasiEntryCatatanPanen.route) {
            EntryCtpnScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeCatatanPanen.route) {
                        popUpTo(DestinasiHomeCatatanPanen.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigate = {
                    navController.navigate(DestinasiHomeCatatanPanen.route) {
                        popUpTo(DestinasiHomeCatatanPanen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = DestinasiDetailCatatanPanen.route,
            arguments = listOf(navArgument(DestinasiDetailCatatanPanen.idCatatanPanenArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idPanen = backStackEntry.arguments?.getString(DestinasiDetailCatatanPanen.idCatatanPanenArg) ?: ""
            DetailScreenCatatanPanen(
                idPanen = idPanen,
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { id ->
                    navController.navigate("catatanpanen_edit/$id")
                }
            )
        }

        composable(
            route = DestinasiEditCatatanPanen.route,
            arguments = listOf(navArgument(DestinasiEditCatatanPanen.idCatatanPanenArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idPanen = backStackEntry.arguments?.getString(DestinasiEditCatatanPanen.idCatatanPanenArg)
            EditScreenCatatanPanen(
                idPanen = idPanen ?: "",
                navigateBack = { navController.navigateUp() },
                onNavigateBack = {
                    navController.navigate("CatatanPanen_detail/$idPanen") {
                        popUpTo(DestinasiEditCatatanPanen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        //RUTE UNTUK AKTIVITAS PERTANIAN
        composable(DestinasiHomeAktivitasPertanian.route) {
            HomeAktivitasPertanianScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryAktivitasPertanian.route) },
                navigateBack = { navController.navigateUp() },
                onDetailClick = { idAktivitas ->
                    navController.navigate("aktivitaspertanian_detail/$idAktivitas")
                },
            )
        }

        composable(DestinasiEntryAktivitasPertanian.route) {
            EntryAktScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeAktivitasPertanian.route) {
                        popUpTo(DestinasiHomeAktivitasPertanian.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigate = {
                    navController.navigate(DestinasiHomeAktivitasPertanian.route) {
                        popUpTo(DestinasiHomeAktivitasPertanian.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = DestinasiDetailAktivitasPertanian.route,
            arguments = listOf(navArgument(DestinasiDetailAktivitasPertanian.idAktivitasPertanianArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idAktivitas = backStackEntry.arguments?.getString(DestinasiDetailAktivitasPertanian.idAktivitasPertanianArg) ?: ""
            DetailScreenAktivitasPertanian(
                idAktivitas = idAktivitas,
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { id ->
                    navController.navigate("aktivitaspertanian_edit/$id")
                }
            )
        }

        composable(
            route = DestinasiEditAktivitasPertanian.route,
            arguments = listOf(navArgument(DestinasiEditAktivitasPertanian.idAktivitasPertanianArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idAktivitas = backStackEntry.arguments?.getString(DestinasiEditAktivitasPertanian.idAktivitasPertanianArg)
            EditScreenAktivitasPertanian(
                idAktivitas = idAktivitas ?: "",
                navigateBack = { navController.navigateUp() },
                onNavigateBack = {
                    navController.navigate("AktivitasPertanian_detail/$idAktivitas") {
                        popUpTo(DestinasiEditAktivitasPertanian.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}