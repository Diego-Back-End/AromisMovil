package com.example.aromismovil.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Esta clase representa la tabla "productos" dentro de la base de datos Room.
// Cada producto guardado en la app corresponde a una fila en esta tabla.
@Entity(tableName = "productos")
data class ProductoEntity(

    // Clave primaria de la tabla.
    // Se genera automáticamente cada vez que se agrega un nuevo producto.
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // Nombre del producto (por ejemplo: “Perfume Dior Sauvage”).
    val nombre: String,

    // Precio del producto en formato numérico.
    val precio: Double,

    // Descripción del producto, donde se detallan sus características.
    val descripcion: String,

    // Referencia al recurso de imagen (guardada en los archivos del proyecto).
    // Se usa para mostrar la foto del producto en la interfaz.
    val imagenRes: Int,

    // Cantidad de unidades disponibles del producto en el inventario.
    val stock: Int
)
