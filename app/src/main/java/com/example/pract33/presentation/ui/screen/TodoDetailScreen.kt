package com.example.pract33.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pract33.domain.model.TodoItem
import com.example.pract33.presentation.viewmodel.TodoListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditScreen(
    viewModel: TodoListViewModel,
    todoId: Int?,
    onBack: () -> Unit
) {
    var existingTodo by remember { mutableStateOf<TodoItem?>(null) }

    LaunchedEffect(todoId) {
        existingTodo = todoId?.let { viewModel.getTodoById(it) }
    }

    var title by remember(existingTodo) { mutableStateOf(existingTodo?.title.orEmpty()) }
    var description by remember(existingTodo) { mutableStateOf(existingTodo?.description.orEmpty()) }

    val isEditMode = todoId != null && existingTodo != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (isEditMode) "Редактировать задачу" else "Новая задача")
                },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Заголовок") }
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                label = { Text("Описание") }
            )

            Button(
                onClick = {
                    if (isEditMode) {
                        viewModel.updateTodo(
                            todo = existingTodo!!.copy(
                                title = title,
                                description = description
                            ),
                            onDone = onBack
                        )
                    } else {
                        viewModel.addTodo(
                            title = title,
                            description = description,
                            onDone = onBack
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank() || description.isNotBlank()
            ) {
                Text("Сохранить")
            }

            if (isEditMode) {
                Button(
                    onClick = {
                        viewModel.deleteTodo(existingTodo!!, onDone = onBack)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Удалить")
                }
            }
        }
    }
}