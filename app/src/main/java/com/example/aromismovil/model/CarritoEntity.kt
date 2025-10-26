package com.example.aromismovil.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Esta clase representa la tabla "carrito" dentro de la base de datos Room.
// Cada objeto de esta clase corresponde a una fila dentro de esa tabla.
@Entity(tableName = "carrito")
data class CarritoEntity(

    // Clave primaria de la tabla.
    // Se genera automáticamente cada vez que se agrega un nuevo producto al carrito.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Identificador del producto que se agrega al carrito.
    // Sirve para relacionarlo con la tabla de productos.
    val productoId: Int,

    // Cantidad de unidades del producto que el usuario agrega.
    // Por defecto parte con 1.
    val cantidad: Int = 1
)
