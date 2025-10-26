package com.example.aromismovil.repository

import com.example.aromismovil.model.ProductoDao
import com.example.aromismovil.model.ProductoEntity
import kotlinx.coroutines.flow.Flow

// Esta clase funciona como un intermediario entre el ViewModel y el DAO de productos.
// Centraliza todas las operaciones que se realizan sobre la tabla "productos".
class ProductoRepository(private val dao: ProductoDao) {

    // Obtiene todos los productos almacenados en la base de datos.
    // Usa Flow para que los cambios se reflejen automáticamente en la interfaz.
    fun obtenerProductos(): Flow<List<ProductoEntity>> = dao.obtenerProductos()

    // Inserta un nuevo producto en la base de datos usando el método del DAO.
    suspend fun insertar(p: ProductoEntity) = dao.insertar(p)

    // Actualiza los datos de un producto existente (por ejemplo, precio o stock).
    suspend fun actualizar(p: ProductoEntity) = dao.actualizar(p)

    // Elimina un producto específico según su ID.
    suspend fun eliminar(id: Int) = dao.eliminar(id)
}
