package com.example.aromismovil.model.remote

import com.example.aromismovil.model.Post
import retrofit2.http.GET

interface ApiService {

    // FakeStore: https://fakestoreapi.com/products
    @GET("products")
    suspend fun getPosts(): List<Post>   // ahora devuelve una lista de productos
}
