package com.example.mypokedex.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mypokedex.di.Injection
import com.example.mypokedex.ui.detail.PokemonDetailActivity
import com.example.mypokedex.ui.login.LoginActivity
import com.example.mypokedex.ui.menu.list.PokemonListScreen
import com.example.mypokedex.ui.menu.list.PokemonListViewModel
import com.example.mypokedex.ui.menu.profile.ProfileScreen
import com.example.mypokedex.ui.view.navigation.MainBottomBar
import com.example.mypokedex.ui.view.navigation.TwoButtonNav
import com.example.mypokedex.utils.DoubleBackToExit
import com.example.mypokedex.utils.SessionManager
import com.example.mypokedex.ui.menu.profile.ProfileViewModel
import com.example.mypokedex.ui.theme.MyPokedexTheme
import com.example.mypokedex.ui.view.topbar.PokedexTopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val rootScreens = listOf(TwoButtonNav.Home.route, TwoButtonNav.Profile.route)
            val context = LocalContext.current

            val factory = Injection.provideFactory(context)
            val viewModel: PokemonListViewModel = viewModel(factory = factory)

            val pagingItems = viewModel.pokemonPagingData.collectAsLazyPagingItems()

            if (currentRoute in rootScreens) {
                DoubleBackToExit()
            }

            MyPokedexTheme{
                Scaffold(
                    topBar = { PokedexTopBar() },
                    bottomBar = { MainBottomBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = TwoButtonNav.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(TwoButtonNav.Home.route) {
                            // 1. Ambil state query dari ViewModel
                            val searchQuery by viewModel.searchQuery.collectAsState()

                            PokemonListScreen(
                                pokemon = pagingItems,
                                // 2. Tambahkan parameter yang diminta oleh fungsi baru
                                searchQuery = searchQuery,
                                onSearchQueryChange = { newQuery ->
                                    viewModel.onSearchQueryChange(newQuery)
                                },
                                onItemClick = { pokemon ->
                                    val intent = Intent(context, PokemonDetailActivity::class.java).apply {
                                        putExtra("POKEMON_NAME", pokemon.name)
                                    }
                                    context.startActivity(intent) // Gunakan context.startActivity
                                }
                            )
                        }
                        composable(TwoButtonNav.Profile.route) {
                            // Memanggil ViewModel menggunakan Factory dari Injection
                            val factory = Injection.provideFactory(context)
                            val profileViewModel: ProfileViewModel = viewModel(factory = factory)

                            // Observasi State
                            val user by profileViewModel.userState.collectAsState()
                            val isLoading by profileViewModel.isLoading.collectAsState()

                            // Trigger data
                            LaunchedEffect(Unit) {
                                profileViewModel.loadProfile()
                            }

                            // Tampilkan UI
                            ProfileScreen(
                                user = user,
                                isLoading = isLoading,
                                onLogout = {
                                    SessionManager.clearSession(context)
                                    val intent = Intent(context, LoginActivity::class.java).apply {
                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    }
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}