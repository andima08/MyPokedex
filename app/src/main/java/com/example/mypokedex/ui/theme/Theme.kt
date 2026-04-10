package com.example.mypokedex.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// 1. Skema Warna Terang (Light Mode) - Dioptimalkan untuk kenyamanan
private val LightColorScheme = lightColorScheme(
    primary = PokemonBlue,         // Biru sebagai identitas utama
    secondary = PokemonBlue,       // Konsisten dengan warna primer
    tertiary = PokemonYellow,      // Kuning untuk aksen kecil
    background = WhiteBg,          // Putih bersih
    surface = Color.White,         // Kartu/Card tetap putih bersih
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextDark,       // Teks abu tua (bukan hitam pekat)
    onSurface = TextDark,
    outline = NavInactive          // Digunakan untuk border atau divider
)

// 2. Skema Warna Gelap (Dark Mode)
private val DarkColorScheme = darkColorScheme(
    primary = LightBlueBg,         // Biru lebih muda agar kontras di gelap
    secondary = PokemonBlue,
    tertiary = PokemonYellow,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun MyPokedexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Tetap false agar brand MyPokedex konsisten
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}