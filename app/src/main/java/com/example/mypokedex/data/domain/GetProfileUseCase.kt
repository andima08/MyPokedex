package com.example.mypokedex.data.domain

import com.example.mypokedex.data.model.UserModel
import com.example.mypokedex.data.repository.local.UserLocalRepository

class GetProfileUseCase(private val repository: UserLocalRepository) {
    suspend operator fun invoke(username: String): UserModel? {
        return repository.getUser(username)
    }
}