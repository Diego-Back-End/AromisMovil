package com.example.aromismovil.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Esta interfaz define las operaciones que se pueden realizar sobre la tabla "pedidos".
// Room la usa para acceder y modificar los datos de los pedidos en la base de datos.
@Dao
interface PedidoDao {

    // Consulta que obtiene todos los pedidos guardados en la base de datos.
    // Se ordenan por fecha descendente (los más recientes aparecen primero).
    // El uso de Flow permite que los cambios se actualicen automáticamente en la app.
    @Query("SELECT * FROM pedidos ORDER BY fecha DESC")
    fun obtenerPedidos(): Flow<List<PedidoEntity>>

    // Inserta un nuevo pedido en la tabla "pedidos".
    // Si ya existe un pedido con el mismo ID, lo reemplaza automáticamente.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun crearPedido(pedido: PedidoEntity)
}
