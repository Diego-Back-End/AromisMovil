package com.example.aromismovil.repository

import com.example.aromismovil.model.Post
import com.example.aromismovil.model.remote.ApiService
import com.example.aromismovil.model.remote.RetrofitInstance

class PostRepository(
    private val api: ApiService = RetrofitInstance.api
) {
    suspend fun getPosts(): List<Post> {
        return api.getPosts()
    }
}
