package com.example.pract33.domain.usecase

import com.example.pract33.domain.repository.TodoPreferencesRepository

class GetCompletedColorEnabledUseCase(
    private val repository: TodoPreferencesRepository
) {
    operator fun invoke() = repository.completedColorEnabled
}