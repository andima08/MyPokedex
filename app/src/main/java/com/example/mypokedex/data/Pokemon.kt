package com.example.mypokedex.data

data class Pokemon(
    val name: String,
    val url: String
)

fun getDummyPokemon(): List<Pokemon> {
    return listOf(
        Pokemon("Budi Santoso", "budi@example.com"),
        Pokemon("Siti Aminah", "siti@example.com"),
        Pokemon("Andi Wijaya", "andi@example.com"),
        Pokemon("Dewi Lestari", "dewi@example.com"),
        Pokemon("Eko Prasetyo", "eko@example.com"),
        Pokemon("Budi Santoso", "budi@example.com"),
        Pokemon("Siti Aminah", "siti@example.com"),
        Pokemon("Andi Wijaya", "andi@example.com"),
        Pokemon("Dewi Lestari", "dewi@example.com"),
        Pokemon("Eko Prasetyo", "eko@example.com"),
        Pokemon( "Budi Santoso", "budi@example.com"),
        Pokemon( "Siti Aminah", "siti@example.com"),
        Pokemon("Andi Wijaya", "andi@example.com"),
        Pokemon("Dewi Lestari", "dewi@example.com"),
        Pokemon( "Eko Prasetyo", "eko@example.com"),
        Pokemon( "Budi Santoso", "budi@example.com"),
        Pokemon( "Siti Aminah", "siti@example.com"),
        Pokemon("Andi Wijaya", "andi@example.com"),
        Pokemon("Dewi Lestari", "dewi@example.com"),
        Pokemon("Eko Prasetyo", "eko@example.com"),
    )
}
