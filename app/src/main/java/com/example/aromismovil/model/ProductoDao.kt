package com.example.aromismovil.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos ORDER BY id ASC")
    fun obtenerProductos(): Flow<List<ProductoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: ProductoEntity)

    @Update
    suspend fun actualizar(producto: ProductoEntity)

    @Query("DELETE FROM productos WHERE id = :id")
    suspend fun eliminar(id: Int)
}
