package com.example.pract33.domain.usecase

import com.example.pract33.domain.model.TodoItem
import com.example.pract33.domain.repository.TodoRepository

class FakeTodoRepository : TodoRepository {

    private val todos = mutableListOf(
        TodoItem(1, "Купить молоко", "2 литра", false),
        TodoItem(2, "Позвонить маме", "Вечером", true),
        TodoItem(3, "Сделать ДЗ", "Android", false)
    )

    override suspend fun getTodos(): List<TodoItem> = todos

    override suspend fun toggleTodo(id: Int) {
        val index = todos.indexOfFirst { it.id == id }
        if (index != -1) {
            val todo = todos[index]
            todos[index] = todo.copy(isCompleted = !todo.isCompleted)
        }
    }
}