package com.example.mypokedex.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import com.example.mypokedex.data.db.CouchbaseManager
import com.example.mypokedex.ui.menu.MainActivity
import com.example.mypokedex.ui.register.RegisterActivity
import com.example.mypokedex.ui.theme.MyPokedexTheme
import com.example.mypokedex.ui.view.bg.PokedexBaseBackground
import com.example.mypokedex.utils.SessionManager
import com.example.mypokedex.utils.Utils
import kotlinx.coroutines.delay

// ... import lainnya tetap sama

class LoginActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CouchbaseManager.init(applicationContext)

        val savedUsername = SessionManager.getUsername(this)
        if (!savedUsername.isNullOrEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            PokedexBaseBackground {
                LoginScreen(
                    onLoginClick = { user, pass ->
                        // --- MODIFIKASI DIMULAI DI SINI ---

                        // 1. Validasi Input Kosong sebelum menyentuh Database
                        if (user.isBlank() || pass.isBlank()) {
                            Toast.makeText(this, "Username dan Password wajib diisi!", Toast.LENGTH_SHORT).show()
                        } else {
                            // 2. Jika tidak kosong, baru jalankan pengecekan
                            val loginSuccess = checkLogin(user, pass)
                            if (loginSuccess) {
                                SessionManager.saveUsername(this, user)
                                Toast.makeText(this, "Selamat Datang, $user!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        // --- MODIFIKASI SELESAI ---
                    },
                    onRegisterClick = {
                        startActivity(Intent(this, RegisterActivity::class.java))
                    }
                )
            }
        }
    }

    private fun checkLogin(user: String, pass: String): Boolean {
        // Pertahanan ekstra: Couchbase akan crash jika ID kosong
        val docId = user.lowercase().trim()
        if (docId.isEmpty()) return false

        val db = CouchbaseManager.getDatabase() ?: return false
        val doc = db.getDocument(docId)

        if (doc != null) {
            val storedHash = doc.getString("password")
            val hashedPasswordInput = Utils.HashUtils.hashPassword(pass)
            return storedHash == hashedPasswordInput
        }
        return false
    }
}