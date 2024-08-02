package com.example.dog_app.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogPreference @Inject constructor(
    private val context: Context
) {

    companion object {
        const val SECURE_FILE_NAME = "dog_preps.xml"

        val KEY_FAVORITE_IMAGES = stringPreferencesKey("KEY_FAVORITE_IMAGES")
        val KEY_FAVORITE_BREEDS = stringPreferencesKey("KEY_FAVORITE_BREEDS")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = SECURE_FILE_NAME,
    )

    fun saveUserPreference(key: Preferences.Key<String>, value: String) {
        runBlocking {
            withContext(Dispatchers.Default) {
                context.dataStore.edit {
                    it[key] = value
                }
            }
        }
    }

    fun getUserPreference(key: Preferences.Key<String>): String? {
        return runBlocking {
            withContext(Dispatchers.IO) {
                val data = context.dataStore.getValueFlow(key, "").first()
                var value = ""
                try {
                    value = data
                    value
                } catch (e: Exception) {
                    e.printStackTrace()
                    value
                }
            }
        }
    }

    private fun <T> DataStore<Preferences>.getValueFlow(
        key: Preferences.Key<T>,
        defaultValue: T,
    ): Flow<T> {
        return this.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[key] ?: defaultValue
            }
    }
}