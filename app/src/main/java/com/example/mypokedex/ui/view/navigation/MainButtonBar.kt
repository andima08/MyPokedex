package com.example.mypokedex.ui.view.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MainBottomBar(navController: NavHostController) {
    val items = listOf(TwoButtonNav.Home, TwoButtonNav.Profile)

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    // Warna ikon saat dipilih (Gunakan Kuning sesuai logo)
                    selectedIconColor = Color(0xFFFFEB3B), // Contoh Kuning Pokemon (Kuning Cerah)

                    // Warna teks saat dipilih (Biasanya Kuning juga)
                    selectedTextColor = Color(0xFFFFEB3B),

                    // Kamu juga bisa mengatur warna saat TIDAK dipilih agar kontras
                    // (Default biasanya abu-abu, sudah bagus)
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray,

                    // Opsional: Warna background pil di belakang ikon (hilangkan jika tidak ingin ada pil)
                    indicatorColor = Color.Transparent // Menghilangkan pil ungu muda default
                ),
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Agar tidak menumpuk halaman saat tombol diklik berkali-kali
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }

            )
        }
    }
}