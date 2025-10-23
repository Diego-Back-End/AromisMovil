package com.example.aromismovil.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {
    @Query("SELECT * FROM carrito")
    fun obtenerCarrito(): Flow<List<CarritoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarAlCarrito(item: CarritoEntity)

    @Query("DELETE FROM carrito WHERE id = :id")
    suspend fun eliminarDelCarrito(id: Int)

    @Query("DELETE FROM carrito")
    suspend fun limpiarCarrito()
}
