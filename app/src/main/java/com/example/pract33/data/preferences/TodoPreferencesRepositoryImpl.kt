package com.example.pract33.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.pract33.domain.repository.TodoPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class TodoPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : TodoPreferencesRepository {

    private companion object {
        val COMPLETED_COLOR_ENABLED = booleanPreferencesKey("completed_color_enabled")
    }

    override val completedColorEnabled: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[COMPLETED_COLOR_ENABLED] ?: true
        }

    override suspend fun setCompletedColorEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[COMPLETED_COLOR_ENABLED] = enabled
        }
    }
}