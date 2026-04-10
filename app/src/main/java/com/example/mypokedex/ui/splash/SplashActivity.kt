package com.example.mypokedex.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mypokedex.data.db.CouchbaseManager
import com.example.mypokedex.ui.login.LoginActivity // Pastikan import LoginActivity
import com.example.mypokedex.ui.menu.MainActivity
import com.example.mypokedex.ui.theme.MyPokedexTheme
import com.example.mypokedex.ui.view.bg.PokedexBaseBackground
import com.example.mypokedex.utils.SessionManager

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CouchbaseManager.init(applicationContext)
        enableEdgeToEdge()

        setContent {
            MyPokedexTheme { // Gunakan tema aplikasi Anda
                PokedexBaseBackground {
                    // Masukkan logika pindah halaman ke dalam onTimeout
                    SplashScreen(onTimeout = {
                        navigateToNextScreen()
                    })
                }
            }
        }
    }

    private fun navigateToNextScreen() {
        // Cek sesi login
        val savedUsername = SessionManager.getUsername(this)

        val intent = if (!savedUsername.isNullOrEmpty()) {
            // Jika sudah login -> Ke Main
            Intent(this, MainActivity::class.java)
        } else {
            // Jika belum login -> Ke Login
            Intent(this, LoginActivity::class.java)
        }

        startActivity(intent)
        finish() // Hapus SplashActivity dari backstack
    }
}