package com.example.pract33.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class TodoPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val COMPLETED_COLOR_ENABLED = booleanPreferencesKey("completed_color_enabled")
    }

    val completedColorEnabled: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(androidx.datastore.preferences.core.emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[COMPLETED_COLOR_ENABLED] ?: true
        }

    suspend fun setCompletedColorEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[COMPLETED_COLOR_ENABLED] = enabled
        }
    }
}