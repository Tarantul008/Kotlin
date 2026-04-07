package com.example.pract33.domain.usecase

import com.example.pract33.domain.repository.TodoPreferencesRepository

class SetCompletedColorEnabledUseCase(
    private val repository: TodoPreferencesRepository
) {
    suspend operator fun invoke(enabled: Boolean) {
        repository.setCompletedColorEnabled(enabled)
    }
}