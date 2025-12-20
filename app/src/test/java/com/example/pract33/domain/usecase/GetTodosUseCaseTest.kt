package com.example.pract33.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTodosUseCaseTest {

    @Test
    fun `getTodos returns 3 items`() = runBlocking {
        val repository = FakeTodoRepository()
        val useCase = GetTodosUseCase(repository)

        val result = useCase()

        assertEquals(3, result.size)
    }
}