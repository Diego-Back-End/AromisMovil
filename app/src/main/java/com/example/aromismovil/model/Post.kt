package com.example.aromismovil.model

// Ahora este "Post" en verdad es un producto de la API FakeStore
data class Post(
    val id: Int,
    val title: String,       // nombre del producto
    val price: Double,       // precio
    val description: String, // descripción del producto
    val category: String,    // categoría (men's clothing, women's clothing, etc.)
    val image: String        // URL de la imagen
)
