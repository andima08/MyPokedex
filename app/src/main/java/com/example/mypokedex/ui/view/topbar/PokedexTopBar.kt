package com.example.mypokedex.ui.view.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mypokedex.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Tambahkan Logo di sini
                Image(
                    painter = painterResource(id = R.drawable.ic_pokedex_logo), // Ganti dengan ID logo kamu
                    contentDescription = "Pokedex Logo",
                    modifier = Modifier.size(width = 100.dp, height = 60.dp) // Atur ukuran logo
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}