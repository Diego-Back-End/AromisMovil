package com.example.aromismovil.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    //  Devuelve todos los productos como Flow (para la UI)
    @Query("SELECT * FROM productos ORDER BY id ASC")
    fun obtenerProductos(): Flow<List<ProductoEntity>>

    //  Consulta directa sin Flow (para inicialización)
    @Query("SELECT * FROM productos")
    suspend fun obtenerProductosDirecto(): List<ProductoEntity>

    //  Insertar producto (si ya existe, lo reemplaza)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: ProductoEntity)

    //  Actualizar producto
    @Update
    suspend fun actualizar(producto: ProductoEntity)

    //  Eliminar producto por id
    @Query("DELETE FROM productos WHERE id = :id")
    suspend fun eliminar(id: Int)
}
