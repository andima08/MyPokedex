package com.example.mypokedex.ui.menu.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.data.domain.GetProfileUseCase
import com.example.mypokedex.data.domain.GetSessionUseCase
import com.example.mypokedex.data.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// File: ui/menu/profile/ProfileViewModel.kt
class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val getSessionUseCase: GetSessionUseCase // Tambahkan ini
) : ViewModel() {

    private val _userState = MutableStateFlow<UserModel?>(null)
    val userState = _userState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            _isLoading.value = true

            // 1. Ambil username dari session usecase
            val username = getSessionUseCase.execute()

            if (username != null) {
                // 2. Cari data lengkapnya di Couchbase lewat profile usecase
                val result = getProfileUseCase(username)
                _userState.value = result
            }

            _isLoading.value = false
        }
    }
}