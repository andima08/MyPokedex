package com.example.mypokedex.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SessionManager {
    private const val PREF_NAME = "secure_session"

    // Fungsi internal untuk membuat instance SharedPreferences yang terenkripsi
    private fun getEncryptedPrefs(context: Context) = EncryptedSharedPreferences.create(
        PREF_NAME,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // Simpan Username
    fun saveUsername(context: Context, username: String) {
        getEncryptedPrefs(context).edit().putString("username", username).apply()
    }

    // Ambil Username
    fun getUsername(context: Context): String? {
        return getEncryptedPrefs(context).getString("username", null)
    }

    // Hapus Session (Untuk Logout)
    fun clearSession(context: Context) {
        getEncryptedPrefs(context).edit().clear().apply()
    }
}