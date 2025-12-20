package com.example.pract33.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pract33.data.local.TodoJsonDataSource
import com.example.pract33.data.repository.TodoRepositoryImpl
import com.example.pract33.domain.usecase.GetTodosUseCase
import com.example.pract33.domain.usecase.ToggleTodoUseCase

class TodoViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dataSource = TodoJsonDataSource(context)
        val repository = TodoRepositoryImpl(dataSource)

        return TodoListViewModel(
            getTodosUseCase = GetTodosUseCase(repository),
            toggleTodoUseCase = ToggleTodoUseCase(repository)
        ) as T
    }
}
