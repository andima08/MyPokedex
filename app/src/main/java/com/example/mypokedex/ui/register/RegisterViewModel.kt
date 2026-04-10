package com.example.mypokedex.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.data.domain.RegisterUseCase
import com.example.mypokedex.data.model.UserModel
import com.example.mypokedex.data.repository.local.UserLocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    private val _registerStatus = MutableStateFlow<Boolean?>(null)
    val registerStatus: StateFlow<Boolean?> = _registerStatus

    fun register(user: UserModel) {
        viewModelScope.launch {
            // Memulai proses di background melalui UseCase
            val result = registerUseCase(user)
            // Mengirim hasil kembali ke UI melalui StateFlow
            _registerStatus.value = result
        }
    }

    fun resetStatus() { _registerStatus.value = null }
}