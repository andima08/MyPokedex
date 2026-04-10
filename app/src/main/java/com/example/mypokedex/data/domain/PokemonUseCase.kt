package com.example.mypokedex.data.domain

import com.example.mypokedex.data.model.PokemonDetailEntity
import com.example.mypokedex.data.repository.local.PokemonLocalRepository
import com.example.mypokedex.data.repository.remote.PokemonRemoteRepository

class GetPokemonPagingUseCase(
    private val remoteRepo: PokemonRemoteRepository,
    private val localRepo: PokemonLocalRepository
) {
    operator fun invoke(query: String) = remoteRepo.getPokemonPaging(localRepo, query)
}
class GetPokemonDetailUseCase(private val remoteRepo: PokemonRemoteRepository) {
    suspend operator fun invoke(name: String): PokemonDetailEntity {
        return remoteRepo.getPokemonDetail(name)
    }
}