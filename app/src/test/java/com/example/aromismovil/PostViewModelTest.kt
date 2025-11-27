package com.example.aromismovil

import com.example.aromismovil.model.Post
import com.example.aromismovil.repository.PostRepository
import com.example.aromismovil.viewmodel.PostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: PostRepository
    private lateinit var viewModel: PostViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = PostViewModel(repository = repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchPosts actualiza posts cuando el repositorio devuelve datos`() = runTest {
        val fakePosts = listOf(
            Post(1, "Prod Test", 15.0, "Desc test", "ropa", "https://example.com/img.png")
        )

        whenever(repository.getPosts()).thenReturn(fakePosts)

        viewModel.fetchPosts()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(fakePosts, viewModel.posts.value)
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(null, viewModel.error.value)
    }
}
