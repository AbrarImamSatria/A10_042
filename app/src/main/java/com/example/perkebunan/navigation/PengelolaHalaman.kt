package com.example.perkebunan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        composable(DestinasiHomeTanaman.route) {
            HomeTanamanScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryTanaman.route) },
                onDetailClick = { idTanaman ->
                    navController.navigate("detail/$idTanaman")
                }
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
            route = "detail/{idTanaman}",
            arguments = listOf(navArgument("idTanaman") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idTanaman = backStackEntry.arguments?.getString("idTanaman") ?: ""
            DetailScreenTanaman(
                idTanaman = idTanaman,
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { id ->
                    navController.navigate("item_edit/$id")
                }
            )
        }
        composable(
            route = "item_edit/{idTanaman}",
            arguments = listOf(navArgument("idTanaman") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idTanaman = backStackEntry.arguments?.getString("idTanaman")
            EditScreenTanaman(
                idTanaman = idTanaman ?: "",
                navigateBack = { navController.navigateUp() },
                onNavigateBack = {
                    navController.navigate("detail/$idTanaman") {
                        popUpTo("item_edit/{idTanaman}") {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}