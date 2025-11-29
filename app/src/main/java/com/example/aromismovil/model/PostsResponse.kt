package com.example.aromismovil.model


// Modelo que representa la estructura completa de la respuesta
// que entrega la API externa cuando devuelve una lista de productos.
// Contiene una lista de objetos Post.
data class PostsResponse(
    val posts: List<Post>   // Lista de productos recibidos desde la API

)