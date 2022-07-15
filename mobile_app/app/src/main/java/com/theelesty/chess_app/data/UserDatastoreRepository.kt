package com.theelesty.chess_app.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

@SuppressLint("StaticFieldLeak")
object UserDatastoreRepository {
    private val Context.dataStore by preferencesDataStore(name = "localData")
    private lateinit var context: Context

    fun setAppContext(context: Context){
        this.context = context
    }

    suspend fun save(key: String, data: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { dataStore ->
            dataStore[dataStoreKey] = data
        }
    }

    suspend fun read(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()

        return preferences[dataStoreKey]
    }
}
