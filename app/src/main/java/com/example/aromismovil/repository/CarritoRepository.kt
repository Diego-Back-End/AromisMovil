package com.example.aromismovil.repository

import com.example.aromismovil.model.CarritoDao
import com.example.aromismovil.model.CarritoEntity
import kotlinx.coroutines.flow.Flow

// Esta clase actúa como intermediaria entre el ViewModel y la base de datos.
// El repository organiza las operaciones y se comunica con el DAO.
class CarritoRepository(private val dao: CarritoDao) {

    // Obtiene la lista completa de productos que están en el carrito.
    // Devuelve un Flow, lo que permite actualizar la interfaz cuando cambian los datos.
    fun obtenerCarrito(): Flow<List<CarritoEntity>> = dao.obtenerCarrito()

    // Agrega un producto al carrito usando el método del DAO.
    suspend fun agregar(item: CarritoEntity) = dao.agregarAlCarrito(item)

    // Elimina un producto del carrito según su ID.
    suspend fun eliminar(id: Int) = dao.eliminarDelCarrito(id)

    // Limpia completamente el carrito, dejando la tabla vacía.
    suspend fun limpiar() = dao.limpiarCarrito()
}
