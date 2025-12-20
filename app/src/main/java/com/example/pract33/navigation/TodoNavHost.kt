package com.example.pract33.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pract33.presentation.ui.screen.TodoDetailScreen
import com.example.pract33.presentation.ui.screen.TodoListScreen
import com.example.pract33.presentation.viewmodel.TodoListViewModel

@Composable
fun TodoNavHost(
    viewModel: TodoListViewModel,
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.TodoList.route,
        modifier = modifier
    ) {

        composable(NavRoutes.TodoList.route) {
            TodoListScreen(
                viewModel = viewModel,
                onTodoClick = { todoId ->
                    navController.navigate(
                        NavRoutes.TodoDetail.createRoute(todoId)
                    )
                }
            )
        }

        composable(NavRoutes.TodoDetail.route) { backStackEntry ->
            val todoId = backStackEntry.arguments
                ?.getString("todoId")
                ?.toInt() ?: return@composable

            val todos by viewModel.todos.collectAsState()

            val todo = todos.first { it.id == todoId }

            TodoDetailScreen(
                todo = todo,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
