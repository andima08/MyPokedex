package com.example.mypokedex.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mypokedex.data.model.PokemonDetailEntity
import com.example.mypokedex.di.Injection
import com.example.mypokedex.ui.menu.detail.PokemonDetailViewModel
import com.example.mypokedex.ui.view.topbar.PokedexTopBar
import com.example.mypokedex.utils.UIState

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    viewModel: PokemonDetailViewModel = viewModel(factory = Injection.provideFactory(LocalContext.current))
) {
    val state by viewModel.detailState.collectAsState()

    LaunchedEffect(pokemonName) {
        if (pokemonName.isNotEmpty()) {
            viewModel.getPokemonDetail(pokemonName)
        }
    }

    Scaffold(
        topBar = { PokedexTopBar() },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center // Memastikan konten berada di tengah layar
        ) {
            when (val s = state) {
                is UIState.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
                is UIState.Success -> {
                    PokemonDetailContent(
                        name = pokemonName,
                        pokemonDetailEntity = s.data
                    )
                }
                is UIState.Error -> {
                    Text(
                        text = "Error: ${s.errorMessage}",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonDetailContent(name: String, pokemonDetailEntity: PokemonDetailEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- 1. BAGIAN GAMBAR DENGAN BINGKAI ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PokemonImageCard(
                imageUrl = pokemonDetailEntity.image,
                label = "Normal",
                borderColor = MaterialTheme.colorScheme.primary
            )

            PokemonImageCard(
                imageUrl = pokemonDetailEntity.imageShiny,
                label = "Shiny",
                borderColor = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- 2. NAMA POKEMON DENGAN BACKGROUND ---
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.extraLarge,
            shadowElevation = 2.dp
        ) {
            Text(
                text = name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 12.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- 3. CARD ABILITIES DENGAN BINGKAI PREMIUM ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ABILITIES",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                // Menampilkan daftar Ability dalam bentuk Badge
                pokemonDetailEntity.abilities.forEach { abilityName ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = abilityName.replaceFirstChar { it.uppercase() },
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonImageCard(imageUrl: String?, label: String, borderColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(140.dp),
            shape = MaterialTheme.shapes.extraLarge,
            color = Color.White,
            border = BorderStroke(3.dp, borderColor.copy(alpha = 0.6f)),
            shadowElevation = 8.dp
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = label,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.ExtraBold,
            color = borderColor
        )
    }
}