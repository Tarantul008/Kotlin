package com.example.pract33.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pract33.presentation.ui.screen.TodoEditScreen
import com.example.pract33.presentation.ui.screen.TodoListScreen
import com.example.pract33.presentation.viewmodel.TodoListViewModel

sealed class TodoScreen(val route: String) {
    data object List : TodoScreen("todo_list")
    data object Add : TodoScreen("todo_edit")
    data object Edit : TodoScreen("todo_edit/{todoId}") {
        fun createRoute(todoId: Int): String = "todo_edit/$todoId"
    }
}

@Composable
fun TodoNavHost(
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TodoScreen.List.route,
        modifier = modifier
    ) {
        composable(TodoScreen.List.route) {
            TodoListScreen(
                viewModel = viewModel,
                onTodoClick = { todoId ->
                    navController.navigate(TodoScreen.Edit.createRoute(todoId))
                },
                onAddClick = {
                    navController.navigate(TodoScreen.Add.route)
                }
            )
        }

        composable(TodoScreen.Add.route) {
            TodoEditScreen(
                viewModel = viewModel,
                todoId = null,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = TodoScreen.Edit.route,
            arguments = listOf(
                navArgument("todoId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId")
            TodoEditScreen(
                viewModel = viewModel,
                todoId = todoId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}