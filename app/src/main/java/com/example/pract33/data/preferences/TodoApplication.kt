package com.example.pract33.data.preferences

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.pract33.data.local.TodoDatabase
import com.example.pract33.data.local.TodoJsonDataSource
import com.example.pract33.data.repository.TodoRepositoryImpl
import com.example.pract33.domain.repository.TodoRepository

private const val PREFERENCES_NAME = "todo_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_NAME
)

class TodoApplication : Application() {
    lateinit var todoRepository: TodoRepository
    lateinit var todoPreferencesRepository: com.example.pract33.domain.repository.TodoPreferencesRepository

    override fun onCreate() {
        super.onCreate()

        val database = TodoDatabase.getDatabase(this)
        val dao = database.todoDao()
        val jsonDataSource = TodoJsonDataSource(this)

        todoRepository = TodoRepositoryImpl(
            todoDao = dao,
            jsonDataSource = jsonDataSource
        )

        todoPreferencesRepository = TodoPreferencesRepositoryImpl(dataStore)
    }
}