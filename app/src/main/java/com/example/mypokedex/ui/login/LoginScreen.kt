package com.example.mypokedex.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.mypokedex.R

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    // LOGIKA VALIDASI: Tombol hanya aktif jika kedua field tidak kosong
    val isFormValid = username.isNotBlank() && password.isNotBlank()

    // Helper untuk label dengan bintang merah
    @Composable
    fun RequiredLabel(label: String) {
        Text(buildAnnotatedString {
            append(label)
            withStyle(style = SpanStyle(color = Color.Red)) {
                append(" *")
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState)
            .imePadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pokedex_logo_full),
            contentDescription = "Logo Aplikasi",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp)
        )

        // Field Username dengan Bintang
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { RequiredLabel("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Field Password dengan Bintang
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { RequiredLabel("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol Login dengan Logika Enabled
        Button(
            onClick = { onLoginClick(username, password) },
            enabled = isFormValid, // Mengunci tombol jika form belum valid
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF212121),
                contentColor = Color.White,
                disabledContainerColor = Color.Gray, // Warna saat terkunci
                disabledContentColor = Color.LightGray
            )
        ) {
            Text("Login", fontWeight = FontWeight.Bold)
        }

        // Tombol Register
        TextButton(
            onClick = onRegisterClick,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Belum punya akun? Daftar di sini",
                color = Color(0xFF212121)
            )
        }
    }
}