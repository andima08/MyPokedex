package com.example.mypokedex.ui.menu.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mypokedex.data.domain.GetPokemonPagingUseCase
import com.example.mypokedex.data.model.PokemonListEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class PokemonListViewModel(
    private val getPagingUseCase: GetPokemonPagingUseCase
) : ViewModel() {

    // 1. StateFlow untuk menampung query pencarian
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // 2. Transformasi Flow: Setiap kali _searchQuery berubah, ambil data Paging baru
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pokemonPagingData: Flow<PagingData<PokemonListEntity>> = _searchQuery
        .debounce(500) // Menunggu 500ms agar tidak spam API saat user mengetik
        .distinctUntilChanged() // Hanya proses jika query benar-benar berubah
        .flatMapLatest { query ->
            // Pastikan GetPokemonPagingUseCase kamu sekarang menerima parameter String
            getPagingUseCase(query)
        }
        .cachedIn(viewModelScope)

    // 3. Fungsi untuk dipanggil dari UI saat user mengetik
    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}