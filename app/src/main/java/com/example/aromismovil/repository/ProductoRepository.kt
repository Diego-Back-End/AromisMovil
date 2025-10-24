package com.example.aromismovil.repository

import com.example.aromismovil.model.ProductoDao
import com.example.aromismovil.model.ProductoEntity
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val dao: ProductoDao) {
    fun obtenerProductos(): Flow<List<ProductoEntity>> = dao.obtenerProductos()
    suspend fun insertar(p: ProductoEntity) = dao.insertar(p)
    suspend fun actualizar(p: ProductoEntity) = dao.actualizar(p)
    suspend fun eliminar(id: Int) = dao.eliminar(id)
}
