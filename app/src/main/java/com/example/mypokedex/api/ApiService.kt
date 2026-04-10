package com.example.mypokedex.api

import com.example.mypokedex.data.response.ListPokemonDetail
import com.example.mypokedex.data.response.ListPokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/api/v2/pokemon")
    suspend fun getListPokemon(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ListPokemonResponse
    @GET("/api/v2/pokemon/{id}")
    suspend fun getListPokemonAbilities(
        @Path("id") id: String
    ): ListPokemonDetail
}