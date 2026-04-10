package com.example.mypokedex.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.couchbase.lite.MutableDocument
import com.example.mypokedex.data.db.CouchbaseManager
import com.example.mypokedex.data.model.UserModel
import com.example.mypokedex.di.Injection
import com.example.mypokedex.ui.view.bg.PokedexBaseBackground
import com.example.mypokedex.utils.Utils

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val factory = Injection.provideFactory(this)
            val viewModel: RegisterViewModel = viewModel(factory = factory)
            val registerStatus by viewModel.registerStatus.collectAsState()

            // Coroutine di UI untuk menangani efek samping (Toast/Navigasi)
            LaunchedEffect(registerStatus) {
                registerStatus?.let { isSuccess ->
                    if (isSuccess) {
                        Toast.makeText(this@RegisterActivity, "Berhasil!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Username sudah ada!", Toast.LENGTH_SHORT).show()
                        viewModel.resetStatus()
                    }
                }
            }

            PokedexBaseBackground {
                RegisterScreen(
                    onRegisterClick = { name, user, email, pass, region ->
                        val hashed = Utils.HashUtils.hashPassword(pass)
                        viewModel.register(UserModel(name, user, email, hashed, region))
                    },
                    onBackToLoginClick = { finish() }
                )
            }
        }
    }
}