package com.example.mypokedex.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.mypokedex.ui.theme.MyPokedexTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mypokedex.di.Injection

class PokemonDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemonName = intent.getStringExtra("POKEMON_NAME") ?: ""

        setContent {
            MyPokedexTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    PokemonDetailScreen(pokemonName = pokemonName)
                }
            }
        }
    }
}