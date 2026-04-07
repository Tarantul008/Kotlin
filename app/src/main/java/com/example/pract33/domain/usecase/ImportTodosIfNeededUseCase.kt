package com.example.pract33.domain.usecase

import com.example.pract33.domain.repository.TodoRepository

class ImportTodosIfNeededUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke() {
        repository.importTodosIfNeeded()
    }
}