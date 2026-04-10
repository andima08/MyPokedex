package com.example.mypokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mypokedex.data.domain.GetPokemonPagingUseCase
import com.example.mypokedex.data.domain.GetProfileUseCase
import com.example.mypokedex.data.domain.*
import com.example.mypokedex.ui.menu.detail.PokemonDetailViewModel
import com.example.mypokedex.ui.menu.list.PokemonListViewModel
import com.example.mypokedex.ui.menu.profile.ProfileViewModel
import com.example.mypokedex.ui.register.RegisterViewModel

class ViewModelFactory(
    private val getPokemonPagingUseCase: GetPokemonPagingUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getSessionUseCase: GetSessionUseCase,
    private val detailUseCase: GetPokemonDetailUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PokemonListViewModel::class.java) -> {
                PokemonListViewModel(getPokemonPagingUseCase) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(getProfileUseCase, getSessionUseCase) as T
            }
            modelClass.isAssignableFrom(PokemonDetailViewModel::class.java) -> {
                PokemonDetailViewModel(detailUseCase) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(registerUseCase) as T // userRepo didapat dari constructor factory
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}