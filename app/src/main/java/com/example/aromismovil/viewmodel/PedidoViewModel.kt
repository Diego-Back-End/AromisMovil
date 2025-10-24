package com.example.aromismovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aromismovil.model.ProductoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

data class Pedido(
    val id: Int,
    val productos: List<ProductoEntity>,
    val total: Double,
    val fecha: Date = Date(),
    val direccion: String,
    val estado: String = "En proceso"
)

class PedidoViewModel : ViewModel() {

    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos.asStateFlow()

    fun crearPedido(productos: List<ProductoEntity>, direccion: String) {
        viewModelScope.launch {
            if (productos.isEmpty()) return@launch
            val total = productos.sumOf { it.precio }
            val nuevoId = (_pedidos.value.maxOfOrNull { it.id } ?: 0) + 1
            val nuevoPedido = Pedido(
                id = nuevoId,
                productos = productos,
                total = total,
                direccion = direccion
            )
            _pedidos.update { it + nuevoPedido }
        }
    }
}
