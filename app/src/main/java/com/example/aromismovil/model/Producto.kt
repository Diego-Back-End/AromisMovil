package com.example.aromismovil.model

data class Producto(
    val id: Int = 0,
    val nombre: String = "",
    val precio: Double = 0.0,
    val disponible: Boolean = true,
    val descripcion: String = "",
    val imagenUrl: String = ""
)