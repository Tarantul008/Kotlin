package com.example.pract33.navigation


sealed class NavRoutes(val route: String) {
    object TodoList : NavRoutes("todo_list")
    object TodoDetail : NavRoutes("todo_detail/{todoId}") {
        fun createRoute(todoId: Int) = "todo_detail/$todoId"
    }
}