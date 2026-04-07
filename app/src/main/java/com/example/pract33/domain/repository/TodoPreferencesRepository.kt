package com.example.pract33.domain.repository

import kotlinx.coroutines.flow.Flow

interface TodoPreferencesRepository {
    val completedColorEnabled: Flow<Boolean>
    suspend fun setCompletedColorEnabled(enabled: Boolean)
}