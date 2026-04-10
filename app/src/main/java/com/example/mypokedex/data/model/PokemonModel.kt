package com.example.mypokedex.data.model

data class PokemonListEntity(
    val name: String,
    val url: String
)
data class PokemonDetailEntity(
    val abilities: List<String>,
    val image: String,
    val imageShiny: String
)