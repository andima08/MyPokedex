package com.example.mypokedex.data.domain

import android.content.Context
import com.example.mypokedex.utils.SessionManager

class GetSessionUseCase(private val context: Context) {
    fun execute(): String? {
        return SessionManager.getUsername(context)
    }
}