package com.example.mypokedex.ui.menu.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypokedex.data.model.UserModel
import com.example.mypokedex.ui.theme.LogoutRed
import com.example.mypokedex.ui.theme.NavBackground
import com.example.mypokedex.ui.theme.PokemonBlue
import com.example.mypokedex.ui.theme.PokemonYellow
import com.example.mypokedex.ui.theme.TextDark
import com.example.mypokedex.ui.view.bg.PokedexBaseBackground

@Composable
fun ProfileScreen(
    user: UserModel?,
    isLoading: Boolean,
    onLogout: () -> Unit
) {
    PokedexBaseBackground {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1. Spacing dari atas (Memberikan ruang untuk Status Bar)
                    Spacer(modifier = Modifier.height(60.dp))

                    // 2. Avatar Container dengan Background Lembut
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(PokemonBlue.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(110.dp),
                            tint = PokemonBlue
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 3. Nama User (Headline)
                    Text(
                        text = "Pokemon Trainer",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.5.sp
                        ),
                        color = TextDark
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // 5. Kartu Informasi (Opsional: Memberikan kesan rapi)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            InfoRow(label = "Full Name", value = user?.fullName ?: "-")
                            Divider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)
                            InfoRow(label = "Email", value = user?.email ?: "-")
                            Divider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)
                            InfoRow(label = "Username", value = user?.username ?: "-")
                            Divider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)
                            InfoRow(label = "Region", value = user?.region ?: "-")
                            Divider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)
                            InfoRow(label = "Status", value = "Active Trainer")
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // 6. Tombol Logout dengan Desain Lebih Bold
                    Button(
                        onClick = onLogout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LogoutRed),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text(
                            "Logout Account",
                            color = NavBackground,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray)
        Text(text = value, fontWeight = FontWeight.Bold, color = PokemonBlue)
    }
}