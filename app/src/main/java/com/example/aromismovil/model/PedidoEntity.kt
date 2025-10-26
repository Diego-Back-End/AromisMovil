package com.example.aromismovil.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Esta clase representa la tabla "pedidos" en la base de datos Room.
// Cada objeto de esta clase es un pedido registrado dentro de la aplicación.
@Entity(tableName = "pedidos")
data class PedidoEntity(

    // Clave primaria del pedido.
    // Se genera automáticamente cuando se crea un nuevo registro.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Guarda la fecha en que se realizó el pedido.
    // Se usa el tiempo actual del sistema en milisegundos al crear el pedido.
    val fecha: Long = System.currentTimeMillis(),

    // Monto total del pedido (la suma del carrito o los productos comprados).
    val total: Double,

    // Dirección de envío ingresada por el usuario para recibir el pedido.
    val direccionEnvio: String,

    // Estado del pedido (por defecto comienza como “Pendiente”).
    // Puede cambiar a “Enviado” o “Entregado” más adelante.
    val estado: String = "Pendiente"
)
