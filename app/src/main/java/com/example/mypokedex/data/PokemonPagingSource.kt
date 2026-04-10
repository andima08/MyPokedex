package com.example.mypokedex.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mypokedex.api.ApiService
import com.example.mypokedex.data.model.PokemonListEntity
import com.example.mypokedex.data.repository.local.PokemonLocalRepository
import java.io.IOException

class PokemonPagingSource(
    private val apiService: ApiService,
    private val localRepo: PokemonLocalRepository,
    private val query: String // Parameter query untuk pencarian
) : PagingSource<Int, PokemonListEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListEntity> {
        return try {
            val position = params.key ?: 0

            // Jika ada query pencarian, kita bisa menyesuaikan logikanya
            // Note: PokeAPI list tidak mendukung search query secara default,
            // Jadi kita ambil data dan lakukan filter manual jika diperlukan.

            val response = apiService.getListPokemon(offset = position, limit = params.loadSize)

            // Mapping dan Filter berdasarkan Query
            val allData = response.results?.filterNotNull()?.map {
                PokemonListEntity(it.name ?: "", it.url ?: "")
            } ?: emptyList()

            // Filter data berdasarkan query (case insensitive)
            val filteredData = if (query.isEmpty()) {
                allData
            } else {
                allData.filter { it.name.contains(query, ignoreCase = true) }
            }

            // Simpan data asli (tanpa filter) ke Local untuk cache offline
            if (allData.isNotEmpty()) {
                localRepo.savePokemon(allData)
            }

            LoadResult.Page(
                data = filteredData,
                prevKey = if (position == 0) null else position - params.loadSize,
                nextKey = if (allData.isEmpty()) null else position + params.loadSize
            )
        } catch (e: Exception) {
            // Tangani Kondisi Offline
            if (e is IOException) {
                // Ambil data dari Couchbase berdasarkan query
                val localData = localRepo.getCachedPokemon(query)
                if (localData.isNotEmpty()) {
                    return LoadResult.Page(
                        data = localData,
                        prevKey = null,
                        nextKey = null
                    )
                }
            }
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonListEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize) ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }
}