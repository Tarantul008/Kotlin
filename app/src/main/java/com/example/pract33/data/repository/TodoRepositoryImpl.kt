package com.example.pract33.data.repository

import com.example.pract33.data.local.TodoDao
import com.example.pract33.data.local.TodoEntity
import com.example.pract33.data.local.TodoJsonDataSource
import com.example.pract33.domain.model.TodoItem
import com.example.pract33.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val todoDao: TodoDao,
    private val jsonDataSource: TodoJsonDataSource
) : TodoRepository {

    override fun getTodos(): Flow<List<TodoItem>> {
        return todoDao.getTodos().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTodoById(id: Int): TodoItem? {
        return todoDao.getTodoById(id)?.toDomain()
    }

    override suspend fun addTodo(title: String, description: String) {
        val newId = todoDao.getMaxId() + 1
        todoDao.insertTodo(
            TodoEntity(
                id = newId,
                title = title,
                description = description,
                isCompleted = false
            )
        )
    }

    override suspend fun updateTodo(todo: TodoItem) {
        todoDao.updateTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(todo: TodoItem) {
        todoDao.deleteTodo(todo.toEntity())
    }

    override suspend fun toggleTodo(id: Int) {
        val current = todoDao.getTodoById(id) ?: return
        todoDao.updateTodo(
            current.copy(isCompleted = !current.isCompleted)
        )
    }

    override suspend fun importTodosIfNeeded() {
        if (todoDao.getCount() > 0) return

        val todos = jsonDataSource.getTodos().map { dto ->
            TodoEntity(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                isCompleted = dto.isCompleted
            )
        }
        todoDao.insertTodos(todos)
    }

    private fun TodoEntity.toDomain(): TodoItem {
        return TodoItem(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted
        )
    }

    private fun TodoItem.toEntity(): TodoEntity {
        return TodoEntity(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted
        )
    }
}