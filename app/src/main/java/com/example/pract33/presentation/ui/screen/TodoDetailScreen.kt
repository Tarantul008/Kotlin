package com.example.pract33.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pract33.domain.model.TodoItem

@Composable
fun TodoDetailScreen(
    todo: TodoItem,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = todo.title,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = todo.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (todo.isCompleted) "Выполнено" else "Не выполнено"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onBackClick) {
            Text("Назад")
        }
    }
}
