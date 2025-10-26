package com.example.aromismovil.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Esta interfaz define las operaciones que se pueden hacer sobre la tabla "carrito".
// Room usa este DAO (Data Access Object) para comunicarse con la base de datos.
@Dao
interface CarritoDao {

    // Consulta que obtiene todos los registros del carrito.
    // Flow permite que los cambios se actualicen automáticamente en la interfaz.
    @Query("SELECT * FROM carrito")
    fun obtenerCarrito(): Flow<List<CarritoEntity>>

    // Inserta un nuevo producto al carrito.
    // Si el producto ya existe (mismo ID), lo reemplaza gracias a OnConflictStrategy.REPLACE.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarAlCarrito(item: CarritoEntity)

    // Elimina un producto específico del carrito según su ID.
    @Query("DELETE FROM carrito WHERE id = :id")
    suspend fun eliminarDelCarrito(id: Int)

    // Borra todos los productos del carrito.
    // Se usa, por ejemplo, después de realizar una compra o vaciar el carrito.
    @Query("DELETE FROM carrito")
    suspend fun limpiarCarrito()
}
