package com.example.aromismovil

import com.example.aromismovil.model.Post
import com.example.aromismovil.model.remote.ApiService
import com.example.aromismovil.repository.PostRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify

class PostRepositoryTest {

    @Test
    fun `getPosts devuelve la lista que entrega ApiService`() = runTest {
        val fakePosts = listOf(
            Post(1, "Producto 1", 10.0, "Desc 1", "ropa", "https://example.com/img.png"),
            Post(2, "Producto 2", 20.0, "Desc 2", "ropa", "https://example.com/img2.png")
        )

        val apiMock: ApiService = mock()
        whenever(apiMock.getPosts()).thenReturn(fakePosts)

        val repository = PostRepository(api = apiMock)

        val result = repository.getPosts()

        assertEquals(fakePosts, result)
        verify(apiMock).getPosts()
    }
}
