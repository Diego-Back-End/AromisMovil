package com.example.aromismovil.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {
    @Query("SELECT * FROM pedidos ORDER BY fecha DESC")
    fun obtenerPedidos(): Flow<List<PedidoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun crearPedido(pedido: PedidoEntity)
}
