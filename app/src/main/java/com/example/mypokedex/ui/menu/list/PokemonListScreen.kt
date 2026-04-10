package com.example.mypokedex.ui.menu.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.mypokedex.data.model.PokemonListEntity
import com.example.mypokedex.ui.view.bg.PokedexBaseBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    pokemon: LazyPagingItems<PokemonListEntity>,
    searchQuery: String, // Harus ada
    onSearchQueryChange: (String) -> Unit, // Harus ada
    onItemClick: (PokemonListEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    PokedexBaseBackground {
        Column(modifier = modifier.fillMaxSize()) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Cari Pokémon (misal: Bulbasaur)") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Clear Search")
                        }
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.LightGray,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray,
                    unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                    focusedContainerColor = Color.White,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    unfocusedLeadingIconColor = Color.Gray
                )
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // Render List Item
                items(count = pokemon.itemCount) { index ->
                    val item = pokemon[index]
                    if (item != null) {
                        PokemonCard(
                            item = item,
                            onClick = { onItemClick(item) }
                        )
                    }
                }

                // Handle Loading & Error States
                pokemon.apply {
                    when {
                        // Loading saat awal buka atau refresh search
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        // Loading saat scroll ke bawah (load more)
                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        // Error saat awal load
                        loadState.refresh is LoadState.Error -> {
                            val e = pokemon.loadState.refresh as LoadState.Error
                            item {
                                ErrorMessage(
                                    message = e.error.localizedMessage ?: "Gagal memuat data",
                                    onClickRetry = { retry() }
                                )
                            }
                        }

                        // Error saat load more
                        loadState.append is LoadState.Error -> {
                            val e = pokemon.loadState.append as LoadState.Error
                            item {
                                ErrorMessage(
                                    message = e.error.localizedMessage ?: "Gagal memuat lebih banyak",
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonCard(item: PokemonListEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.name.replaceFirstChar { it.uppercase() } ?: "Unknown",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ErrorMessage(message: String, onClickRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Button(onClick = onClickRetry) {
            Text("Coba Lagi")
        }
    }
}