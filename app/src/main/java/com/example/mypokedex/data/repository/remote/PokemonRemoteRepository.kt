package com.example.mypokedex.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mypokedex.api.ApiService
import com.example.mypokedex.data.PokemonPagingSource
import com.example.mypokedex.data.model.PokemonDetailEntity
import com.example.mypokedex.data.model.PokemonListEntity
import com.example.mypokedex.data.repository.local.PokemonLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PokemonRemoteRepository(private val apiService: ApiService) {

    fun getPokemonPaging(
        localRepo: PokemonLocalRepository,
        query: String
    ): Flow<PagingData<PokemonListEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                PokemonPagingSource(apiService, localRepo, query)
            }
        ).flow
    }

    suspend fun getPokemonDetail(id: String): PokemonDetailEntity = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getListPokemonAbilities(id)
            val abilities = response.abilities?.filterNotNull()?.map {
                it.ability?.name ?: ""
            } ?: emptyList()
            val pokemonImage = response.sprites?.frontDefault ?: ""
            val pokemonImageShiny = response.sprites?.frontShiny ?: ""

            PokemonDetailEntity(abilities = abilities, image = pokemonImage,
                imageShiny = pokemonImageShiny)
        } catch (e: Exception) {
            // Jika offline saat buka detail, kembalikan entity kosong atau lempar error
            PokemonDetailEntity(abilities = emptyList(), image = "", imageShiny = "")
        }
    }
}