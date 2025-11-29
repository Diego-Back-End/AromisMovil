package com.example.aromismovil.model

// Modelo de datos que representa un producto obtenido desde la API externa FakeStore.
// Se utiliza para mapear la estructura JSON que entrega el servicio.
data class Post(
    val id: Int,
    val title: String,       // nombre del producto
    val price: Double,       // precio
    val description: String, // descripción del producto
    val category: String,    // categoría (men's clothing, women's clothing, etc.)
    val image: String        // URL de la imagen
)
