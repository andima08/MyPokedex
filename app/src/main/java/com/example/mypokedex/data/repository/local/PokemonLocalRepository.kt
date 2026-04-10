package com.example.mypokedex.data.repository.local

import com.couchbase.lite.DataSource
import com.couchbase.lite.Database
import com.couchbase.lite.Expression
import com.couchbase.lite.MutableDocument
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import com.example.mypokedex.data.model.PokemonListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonLocalRepository(private val database: Database) {
    suspend fun savePokemon(list: List<PokemonListEntity>) = withContext(Dispatchers.IO) {
        list.forEach { pokemon ->
            val mutableDoc = MutableDocument(pokemon.name)
                .setString("type", "pokemon_cache")
                .setString("name", pokemon.name)
                .setString("url", pokemon.url)
            database.save(mutableDoc)
        }
    }

    fun getCachedPokemon(query1: String): List<PokemonListEntity> {
        val query = QueryBuilder.select(SelectResult.all())
            .from(DataSource.database(database))
            .where(Expression.property("type").equalTo(Expression.string("pokemon_cache")))

        return query.execute().allResults().map { result ->
            val dict = result.getDictionary(database.name)
            PokemonListEntity(dict?.getString("name") ?: "", dict?.getString("url") ?: "")
        }
    }
}