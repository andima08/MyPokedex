package com.example.mypokedex.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.mypokedex.api.ApiConfig
import com.example.mypokedex.data.domain.GetProfileUseCase
import com.example.mypokedex.data.domain.GetSessionUseCase
import com.example.mypokedex.data.db.CouchbaseManager
import com.example.mypokedex.data.domain.GetPokemonDetailUseCase
import com.example.mypokedex.data.repository.remote.PokemonRemoteRepository
import com.example.mypokedex.data.repository.local.UserLocalRepository
import com.example.mypokedex.data.domain.GetPokemonPagingUseCase
import com.example.mypokedex.data.domain.RegisterUseCase
import com.example.mypokedex.data.repository.local.PokemonLocalRepository
import com.example.mypokedex.viewmodel.ViewModelFactory

object Injection {
    fun provideFactory(context: Context): ViewModelProvider.Factory {
        val db = CouchbaseManager.getDatabase() ?: throw IllegalStateException("DB Null")
        val apiService = ApiConfig.getApiService()

        val userRepo = UserLocalRepository(db)
        val pokemonLocalRepo = PokemonLocalRepository(db)
        val pokemonRemoteRepo = PokemonRemoteRepository(apiService)

        val getPokemonPagingUseCase = GetPokemonPagingUseCase(pokemonRemoteRepo, pokemonLocalRepo)
        val detailUseCase = GetPokemonDetailUseCase(pokemonRemoteRepo)
        val registerUseCase = RegisterUseCase(userRepo)
        val getProfileUseCase = GetProfileUseCase(userRepo)
        val getSessionUseCase = GetSessionUseCase(context)

        return ViewModelFactory(
            getPokemonPagingUseCase,
            getProfileUseCase,
            getSessionUseCase,
            detailUseCase,
            registerUseCase
        )
    }
}