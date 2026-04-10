package com.example.mypokedex.utils

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun DoubleBackToExit() {
    val context = LocalContext.current
    var backPressedTime by remember { mutableLongStateOf(0L) }

    BackHandler {
        val currentTime = System.currentTimeMillis()

        // Jika jarak tekan tombol back kurang dari 2 detik
        if (currentTime - backPressedTime < 2000) {
            // Keluar dari aplikasi
            (context as? Activity)?.finish()
        } else {
            // Tampilkan pesan petunjuk
            Toast.makeText(context, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
            backPressedTime = currentTime
        }
    }
}