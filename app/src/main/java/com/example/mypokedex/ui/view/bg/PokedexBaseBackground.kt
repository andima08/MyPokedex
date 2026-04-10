package com.example.mypokedex.ui.view.bg

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.example.mypokedex.ui.theme.LightBlueBg
import com.example.mypokedex.ui.theme.WhiteBg

@Composable
fun PokedexBaseBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(LightBlueBg, WhiteBg)
                )
            )
    ) {
        content()
    }
}