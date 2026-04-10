package com.example.mypokedex.ui.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TwoButtonNav(val route: String, val title: String, val icon: ImageVector) {
    object Home : TwoButtonNav("home", "Pokedex", Icons.Default.Home)
    object Profile : TwoButtonNav("profile", "Profile", Icons.Default.Person)
}