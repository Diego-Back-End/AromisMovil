package com.example.aromismovil.repository

import com.example.aromismovil.model.CarritoDao
import com.example.aromismovil.model.CarritoEntity
import kotlinx.coroutines.flow.Flow

class CarritoRepository(private val dao: CarritoDao) {
    fun obtenerCarrito(): Flow<List<CarritoEntity>> = dao.obtenerCarrito()
    suspend fun agregar(item: CarritoEntity) = dao.agregarAlCarrito(item)
    suspend fun eliminar(id: Int) = dao.eliminarDelCarrito(id)
    suspend fun limpiar() = dao.limpiarCarrito()
}
