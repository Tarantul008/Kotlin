package com.example.pract33.domain.usecase

import com.example.pract33.domain.model.TodoItem
import com.example.pract33.domain.repository.TodoRepository

class GetTodosUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(): List<TodoItem> = repository.getTodos()
}
