package com.example.pract33.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pract33.domain.model.TodoItem
import com.example.pract33.domain.usecase.GetTodosUseCase
import com.example.pract33.domain.usecase.ToggleTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val getTodosUseCase: GetTodosUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase
) : ViewModel() {

    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            _todos.value = getTodosUseCase()
        }
    }

    fun onTodoChecked(todoId: Int) {
        viewModelScope.launch {
            toggleTodoUseCase(todoId)
            _todos.value = getTodosUseCase()
        }
    }
}
