package com.example.pract33.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pract33.presentation.viewmodel.TodoListViewModel
import androidx.compose.ui.platform.testTag

@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel,
    onTodoClick: (Int) -> Unit
) {
    val todos by viewModel.todos.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(todos) { todo ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onTodoClick(todo.id) }
                    .testTag("todo_item_${todo.id}"),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = todo.isCompleted,
                    onCheckedChange = {
                        viewModel.onTodoChecked(todo.id)
                    },
                    modifier = Modifier.testTag("todo_checkbox_${todo.id}")
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.testTag("todo_title_${todo.id}")
                    )
                    Text(
                        text = todo.description,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.testTag("todo_description_${todo.id}")
                    )
                }
            }
        }
    }
}
