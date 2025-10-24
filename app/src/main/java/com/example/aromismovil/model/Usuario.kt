package com.example.aromismovil.model

data class Usuario(
    val nombre: String = "",
    val correo: String = "",
    val rol: String = "Cliente",
    val direccion: String = "",
    val telefono: String = ""
)
