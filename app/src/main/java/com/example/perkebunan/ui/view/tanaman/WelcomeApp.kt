package com.example.perkebunan.ui.view.tanaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.perkebunan.R
import kotlinx.coroutines.delay

object DestinasiWelcomeScreen {
    const val route = "welcome_screen"
}

@Composable
fun WelcomeScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(1500)
        navController.navigate(DestinasiHomeTanaman.route) {
            popUpTo(DestinasiWelcomeScreen.route) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = "App Logo",
            modifier = Modifier.size(300.dp)
        )
    }
}