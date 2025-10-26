package com.example.aromismovil.model

// Esta clase representa el modelo de datos para un usuario dentro de la aplicación.
// No se guarda en base de datos, pero se usa para manejar la información del usuario en memoria.
data class Usuario(

    // Nombre completo del usuario registrado o que inicia sesión.
    val nombre: String = "",

    // Correo electrónico del usuario, utilizado para identificar la cuenta.
    val correo: String = "",

    // Rol del usuario dentro de la aplicación.
    // Por defecto se asigna "Cliente", pero podría ser "Administrador" en otros casos.
    val rol: String = "Cliente",

    // Dirección del usuario, usada por ejemplo para envíos de pedidos.
    val direccion: String = "",

    // Teléfono de contacto del usuario.
    val telefono: String = ""
)
