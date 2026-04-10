package com.example.mypokedex.ui.menu.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.data.domain.GetPokemonDetailUseCase
import com.example.mypokedex.data.model.PokemonDetailEntity
import com.example.mypokedex.utils.UIState // Pastikan import UIState Anda
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val getDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    // 1. Ubah tipe menjadi UIState<PokemonDetailEntity>
    // Inisialisasi awal dengan UIState.Loading agar sinkron dengan saat pertama kali buka
    private val _detailState = MutableStateFlow<UIState<PokemonDetailEntity>>(UIState.Loading)
    val detailState: StateFlow<UIState<PokemonDetailEntity>> = _detailState.asStateFlow()

    fun getPokemonDetail(name: String) {
        viewModelScope.launch {
            // 2. Set status ke Loading saat memulai
            _detailState.value = UIState.Loading
            try {
                val result = getDetailUseCase(name)
                // 3. Masukkan data ke dalam UIState.Success
                _detailState.value = UIState.Success(result)
            } catch (e: Exception) {
                // 4. Masukkan pesan error ke dalam UIState.Error
                _detailState.value = UIState.Error(e.message ?: "Terjadi kesalahan tidak dikenal")
            }
        }
    }
}