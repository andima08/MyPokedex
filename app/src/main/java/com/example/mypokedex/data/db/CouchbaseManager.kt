package com.example.mypokedex.data.db

import android.content.Context
import com.couchbase.lite.*

object CouchbaseManager {
    private var database: Database? = null

    fun init(context: Context) {
        if (database == null) {
            CouchbaseLite.init(context)
            val config = DatabaseConfiguration()
            database = Database("pokedex_db", config)
        }
    }

    fun getDatabase(): Database? = database
}