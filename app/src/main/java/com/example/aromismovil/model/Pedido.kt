package com.example.aromismovil.model

import java.util.Date

data class Pedido(
    val id: Int,
    val productos: List<Producto>,
    val total: Double,
    val fecha: Date = Date(),
    val direccionEnvio: String,
    val estado: String = "Pendiente" // Pendiente | Enviado | Entregado
)
