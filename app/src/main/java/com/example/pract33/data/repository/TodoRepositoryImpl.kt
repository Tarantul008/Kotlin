package com.example.pract33.data.repository

import com.example.pract33.data.local.TodoJsonDataSource
import com.example.pract33.domain.model.TodoItem
import com.example.pract33.domain.repository.TodoRepository

class TodoRepositoryImpl(
    private val dataSource: TodoJsonDataSource
) : TodoRepository {

    private var todos: MutableList<TodoItem> = mutableListOf()

    override suspend fun getTodos(): List<TodoItem> {
        if (todos.isEmpty()) {
            todos = dataSource.getTodos()
                .map {
                    TodoItem(
                        id = it.id,
                        title = it.title,
                        description = it.description,
                        isCompleted = it.isCompleted
                    )
                }
                .toMutableList()
        }
        return todos
    }

    override suspend fun toggleTodo(id: Int) {
        todos = todos.map {
            if (it.id == id) {
                it.copy(isCompleted = !it.isCompleted)
            } else it
        }.toMutableList()
    }
}
