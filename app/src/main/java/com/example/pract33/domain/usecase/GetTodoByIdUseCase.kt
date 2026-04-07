package com.example.pract33.domain.usecase

import com.example.pract33.domain.repository.TodoRepository

class GetTodoByIdUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(id: Int) = repository.getTodoById(id)
}