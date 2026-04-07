package com.example.pract33

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pract33.navigation.TodoNavHost
import com.example.pract33.presentation.viewmodel.TodoListViewModel
import com.example.pract33.ui.theme.Pract33Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Pract33Theme {
                val todoViewModel: TodoListViewModel = viewModel(factory = TodoListViewModel.Factory)
                TodoNavHost(viewModel = todoViewModel)
            }
        }
    }
}