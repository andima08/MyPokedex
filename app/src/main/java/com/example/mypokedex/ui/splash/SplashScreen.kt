package com.example.mypokedex.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mypokedex.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    // Jalankan timer 3 detik
    LaunchedEffect(Unit) {
        delay(3000L)
        onTimeout()
    }

    // Box sekarang transparan (mengikuti background dari PokedexBaseBackground)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pokedex_logo_full),
            contentDescription = "Logo Pokedex",
            modifier = Modifier.size(300.dp)
        )
    }
}