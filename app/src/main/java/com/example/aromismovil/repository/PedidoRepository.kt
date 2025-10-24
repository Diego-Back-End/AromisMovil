package com.example.aromismovil.repository

import com.example.aromismovil.model.PedidoDao
import com.example.aromismovil.model.PedidoEntity
import kotlinx.coroutines.flow.Flow

class PedidoRepository(private val dao: PedidoDao) {
    fun obtenerPedidos(): Flow<List<PedidoEntity>> = dao.obtenerPedidos()
    suspend fun crearPedido(pedido: PedidoEntity) = dao.crearPedido(pedido)
}
