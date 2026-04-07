package com.example.pract33.domain.repository

import com.example.pract33.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<TodoItem>>
    suspend fun getTodoById(id: Int): TodoItem?
    suspend fun addTodo(title: String, description: String)
    suspend fun updateTodo(todo: TodoItem)
    suspend fun deleteTodo(todo: TodoItem)
    suspend fun toggleTodo(id: Int)
    suspend fun importTodosIfNeeded()
}