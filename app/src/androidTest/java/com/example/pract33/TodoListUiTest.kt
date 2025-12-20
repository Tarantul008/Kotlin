package com.example.pract33

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.pract33.navigation.TodoNavHost
import com.example.pract33.presentation.viewmodel.TodoListViewModel
import com.example.pract33.presentation.viewmodel.TodoViewModelFactory
import org.junit.Rule
import org.junit.Test

class TodoListUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    @Test
    fun todoList_showsAllTodos() {
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current

            val viewModel = androidx.lifecycle.ViewModelProvider(
                androidx.lifecycle.ViewModelStore(),
                TodoViewModelFactory(context)
            )[TodoListViewModel::class.java]

            TodoNavHost(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Купить молоко").assertIsDisplayed()
        composeTestRule.onNodeWithText("Позвонить маме").assertIsDisplayed()
        composeTestRule.onNodeWithText("Сделать ДЗ по Android").assertIsDisplayed()
    }


    @Test
    fun checkbox_toggleChangesState() {
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current

            val viewModel = androidx.lifecycle.ViewModelProvider(
                androidx.lifecycle.ViewModelStore(),
                TodoViewModelFactory(context)
            )[TodoListViewModel::class.java]

            TodoNavHost(viewModel = viewModel)
        }

        val checkbox = composeTestRule.onAllNodes(isToggleable())[0]

        checkbox.assertIsOff()
        checkbox.performClick()
        checkbox.assertIsOn()
    }


    @Test
    fun navigation_listToDetailAndBack() {
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current

            val viewModel = androidx.lifecycle.ViewModelProvider(
                androidx.lifecycle.ViewModelStore(),
                TodoViewModelFactory(context)
            )[TodoListViewModel::class.java]

            TodoNavHost(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Купить молоко")
            .performClick()

        composeTestRule.onNodeWithText("2 литра, обезжиренное")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Купить молоко")
            .assertIsDisplayed()
    }
}