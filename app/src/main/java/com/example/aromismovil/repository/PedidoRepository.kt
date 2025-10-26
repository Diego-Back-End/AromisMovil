package com.example.aromismovil.repository

import com.example.aromismovil.model.PedidoDao
import com.example.aromismovil.model.PedidoEntity
import kotlinx.coroutines.flow.Flow

// Esta clase sirve como intermediaria entre el ViewModel y el DAO de pedidos.
// Centraliza las funciones para obtener y crear pedidos dentro de la aplicación.
class PedidoRepository(private val dao: PedidoDao) {

    // Obtiene todos los pedidos registrados en la base de datos.
    // Devuelve un Flow, lo que permite que la interfaz se actualice automáticamente
    // cuando cambien los datos en la tabla de pedidos.
    fun obtenerPedidos(): Flow<List<PedidoEntity>> = dao.obtenerPedidos()

    // Crea un nuevo pedido en la base de datos usando el método del DAO.
    suspend fun crearPedido(pedido: PedidoEntity) = dao.crearPedido(pedido)
}
