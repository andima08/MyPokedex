package com.example.mypokedex.utils

import java.security.MessageDigest

class Utils {
    object HashUtils {
        fun hashPassword(password: String): String {
            val bytes = password.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }
    }

    object ValidationUtils {

        fun isValidEmail(email: String): Boolean {
            return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isStrongPassword(password: String): Boolean {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
            return password.matches(passwordPattern.toRegex())
        }
    }
}

