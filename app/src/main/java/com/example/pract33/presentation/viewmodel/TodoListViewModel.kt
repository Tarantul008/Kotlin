package com.example.pract33.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pract33.data.preferences.TodoApplication
import com.example.pract33.domain.model.TodoItem
import com.example.pract33.domain.usecase.AddTodoUseCase
import com.example.pract33.domain.usecase.DeleteTodoUseCase
import com.example.pract33.domain.usecase.GetCompletedColorEnabledUseCase
import com.example.pract33.domain.usecase.GetTodoByIdUseCase
import com.example.pract33.domain.usecase.GetTodosUseCase
import com.example.pract33.domain.usecase.ImportTodosIfNeededUseCase
import com.example.pract33.domain.usecase.SetCompletedColorEnabledUseCase
import com.example.pract33.domain.usecase.ToggleTodoUseCase
import com.example.pract33.domain.usecase.UpdateTodoUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TodoListUiState(
    val todos: List<TodoItem> = emptyList(),
    val completedColorEnabled: Boolean = true
)

class TodoListViewModel(
    private val getTodosUseCase: GetTodosUseCase,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase,
    private val importTodosIfNeededUseCase: ImportTodosIfNeededUseCase,
    private val getCompletedColorEnabledUseCase: GetCompletedColorEnabledUseCase,
    private val setCompletedColorEnabledUseCase: SetCompletedColorEnabledUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            importTodosIfNeededUseCase()
        }
    }

    val uiState: StateFlow<TodoListUiState> =
        combine(
            getTodosUseCase(),
            getCompletedColorEnabledUseCase()
        ) { todos, completedColorEnabled ->
            TodoListUiState(
                todos = todos,
                completedColorEnabled = completedColorEnabled
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TodoListUiState()
        )

    fun onTodoChecked(todoId: Int) {
        viewModelScope.launch {
            toggleTodoUseCase(todoId)
        }
    }

    fun setCompletedColorEnabled(enabled: Boolean) {
        viewModelScope.launch {
            setCompletedColorEnabledUseCase(enabled)
        }
    }

    fun addTodo(title: String, description: String, onDone: () -> Unit) {
        viewModelScope.launch {
            addTodoUseCase(title, description)
            onDone()
        }
    }

    fun updateTodo(todo: TodoItem, onDone: () -> Unit) {
        viewModelScope.launch {
            updateTodoUseCase(todo)
            onDone()
        }
    }

    fun deleteTodo(todo: TodoItem, onDone: () -> Unit) {
        viewModelScope.launch {
            deleteTodoUseCase(todo)
            onDone()
        }
    }

    suspend fun getTodoById(id: Int): TodoItem? {
        return getTodoByIdUseCase(id)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TodoApplication

                TodoListViewModel(
                    getTodosUseCase = GetTodosUseCase(application.todoRepository),
                    getTodoByIdUseCase = GetTodoByIdUseCase(application.todoRepository),
                    addTodoUseCase = AddTodoUseCase(application.todoRepository),
                    updateTodoUseCase = UpdateTodoUseCase(application.todoRepository),
                    deleteTodoUseCase = DeleteTodoUseCase(application.todoRepository),
                    toggleTodoUseCase = ToggleTodoUseCase(application.todoRepository),
                    importTodosIfNeededUseCase = ImportTodosIfNeededUseCase(application.todoRepository),
                    getCompletedColorEnabledUseCase = GetCompletedColorEnabledUseCase(application.todoPreferencesRepository),
                    setCompletedColorEnabledUseCase = SetCompletedColorEnabledUseCase(application.todoPreferencesRepository)
                )
            }
        }
    }
}