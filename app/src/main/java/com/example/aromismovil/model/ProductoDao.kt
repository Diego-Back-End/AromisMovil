package com.example.aromismovil.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Esta interfaz define todas las operaciones que se pueden hacer sobre la tabla "productos".
// Room la utiliza para comunicarse con la base de datos y ejecutar estas acciones.
@Dao
interface ProductoDao {

    // Consulta que obtiene todos los productos almacenados en la base de datos.
    // Los ordena por su ID de forma ascendente.
    // Flow permite que los cambios se reflejen en tiempo real en la interfaz.
    @Query("SELECT * FROM productos ORDER BY id ASC")
    fun obtenerProductos(): Flow<List<ProductoEntity>>

    // Inserta un nuevo producto en la base de datos.
    // Si el producto ya existe (mismo ID), se reemplaza con los nuevos datos.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: ProductoEntity)

    // Actualiza los datos de un producto existente.
    // Por ejemplo, si cambia su precio o descripción.
    @Update
    suspend fun actualizar(producto: ProductoEntity)

    // Elimina un producto de la base de datos según su ID.
    @Query("DELETE FROM productos WHERE id = :id")
    suspend fun eliminar(id: Int)
}
