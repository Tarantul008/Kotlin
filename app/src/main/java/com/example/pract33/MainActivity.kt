package com.example.pract33

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pract33.navigation.TodoNavHost
import com.example.pract33.ui.theme.Pract33Theme
import com.example.pract33.presentation.ui.screen.TodoListScreen
import com.example.pract33.presentation.viewmodel.TodoListViewModel
import com.example.pract33.presentation.viewmodel.TodoViewModelFactory
import com.example.pract33.ui.theme.Pract33Theme

class MainActivity : ComponentActivity() {
    private val viewModel: TodoListViewModel by viewModels {
        TodoViewModelFactory(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pract33Theme {
                TodoNavHost(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Pract33Theme {
        Greeting("Android")
    }
}