package com.example.mypokedex.data.repository.local

import com.couchbase.lite.Database
import com.couchbase.lite.MutableDocument
import com.example.mypokedex.data.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserLocalRepository(private val database: Database) {

    suspend fun register(user: UserModel): Boolean = withContext(Dispatchers.IO) {
        val docId = user.username.lowercase().trim()

        if (database.getDocument(docId) != null) return@withContext false

        val mutableDoc = MutableDocument(docId)
            .setString("type", "user")
            .setString("fullName", user.fullName)
            .setString("username", user.username)
            .setString("email", user.email)
            .setString("password", user.password)
            .setString("region", user.region)

        return@withContext try {
            database.save(mutableDoc)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUser(username: String): UserModel? = withContext(Dispatchers.IO) {
        val docId = username.lowercase().trim()
        val doc = database.getDocument(docId) ?: return@withContext null

        UserModel(
            fullName = doc.getString("fullName") ?: "",
            username = doc.getString("username") ?: "",
            email = doc.getString("email") ?: "",
            password = doc.getString("password") ?: "",
            region = doc.getString("region") ?: ""
        )
    }
}