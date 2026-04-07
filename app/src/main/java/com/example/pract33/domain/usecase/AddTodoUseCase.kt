package com.example.pract33.domain.usecase

import com.example.pract33.domain.repository.TodoRepository

class AddTodoUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(title: String, description: String) {
        repository.addTodo(title, description)
    }
}