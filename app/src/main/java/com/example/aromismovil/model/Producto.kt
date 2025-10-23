package com.example.aromismovil.model

import androidx.annotation.DrawableRes

data class Producto(
    val id: Int = 0,
    val nombre: String = "",
    val precio: Double = 0.0,
    val disponible: Boolean = true,
    val descripcion: String = "",
    @DrawableRes val imagenRes: Int = 0, // 🔹 en vez de imagenUrl
    val stock: Int = 0,
    val categoria: String = "Ropa",
    val tallas: List<String> = listOf("S", "M", "L", "XL")
)
