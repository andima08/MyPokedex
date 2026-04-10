package com.example.mypokedex.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypokedex.ui.theme.TextDark
import com.example.mypokedex.utils.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String, String, String) -> Unit,
    onBackToLoginClick: () -> Unit
) {
    // State untuk Input Field
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State untuk Region Pokémon
    var selectedRegion by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    val regions = listOf("Kanto", "Johto", "Hoenn", "Sinnoh", "Unova", "Kalos", "Alola", "Galar", "Paldea")

    // State UI
    var isPasswordVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // Validasi Sederhana
    val isEmailValid = remember(email) {
        if (email.isEmpty()) true else Utils.ValidationUtils.isValidEmail(email)
    }
    val isPasswordStrong = remember(password) {
        if (password.isEmpty()) true else Utils.ValidationUtils.isStrongPassword(password)
    }

    // Tombol aktif jika semua field terisi dan valid
    val isFormFilled = fullName.isNotBlank() &&
            username.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank() &&
            selectedRegion.isNotBlank() &&
            isEmailValid &&
            isPasswordStrong

    // Helper untuk Label Required (*)
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
        // --- HEADER ---
        Text(
            text = "Buat Akun Baru",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Text(
            text = "Silakan lengkapi data Pokémon Trainer Anda",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 32.dp),
            color = TextDark
        )

        // --- 1. NAMA LENGKAP ---
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { RequiredLabel("Nama Lengkap") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 2. USERNAME ---
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { RequiredLabel("Username") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 3. EMAIL ---
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { RequiredLabel("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = !isEmailValid,
            supportingText = {
                if (!isEmailValid) {
                    Text("Format email tidak valid", color = MaterialTheme.colorScheme.error)
                }
            },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 4. DROPDOWN REGION ---
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedRegion,
                onValueChange = {},
                readOnly = true,
                label = { RequiredLabel("Pilih Region Asal") },
                leadingIcon = { Icon(Icons.Default.Map, contentDescription = null) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                regions.forEach { region ->
                    DropdownMenuItem(
                        text = { Text(region) },
                        onClick = {
                            selectedRegion = region
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 5. PASSWORD ---
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { RequiredLabel("Password") },
            modifier = Modifier.fillMaxWidth(),
            isError = !isPasswordStrong,
            supportingText = {
                if (!isPasswordStrong) {
                    Text(
                        "Minimal 8 karakter & kombinasi simbol",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
            },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- TOMBOL DAFTAR ---
        Button(
            onClick = { onRegisterClick(fullName, username, email, password, selectedRegion) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormFilled,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = TextDark,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            )
        ) {
            Text("Daftar Sekarang", modifier = Modifier.padding(vertical = 4.dp))
        }

        // --- BACK TO LOGIN ---
        TextButton(
            onClick = onBackToLoginClick,
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Text(text = "Sudah punya akun? Login di sini", color = TextDark)
        }
    }
}