package com.example.mypokedex.data.domain

import com.example.mypokedex.data.model.UserModel
import com.example.mypokedex.data.repository.local.UserLocalRepository

class RegisterUseCase(private val repository: UserLocalRepository) {
    suspend operator fun invoke(user: UserModel): Boolean {
        return repository.register(user)
    }
}