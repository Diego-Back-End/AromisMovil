package com.example.aromismovil.repository

import com.example.aromismovil.model.Post
import com.example.aromismovil.model.remote.ApiService
import com.example.aromismovil.model.remote.RetrofitInstance



// Repositorio encargado de comunicarse con la API externa.
// Separa la lógica de acceso a datos del resto de la aplicación.
class PostRepository(
    private val api: ApiService = RetrofitInstance.api
) {

    // Función suspendida que realiza la solicitud HTTP para obtener la lista de productos.
    // Llama al método definido en ApiService mediante Retrofit.
    suspend fun getPosts(): List<Post> {
        return api.getPosts()
    }
}
