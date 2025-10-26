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

// Clase de datos que representa un pedido realizado por el usuario.
// Incluye los productos, total, fecha, dirección de envío y estado actual del pedido.
data class Pedido(
    val id: Int,                            // Identificador único del pedido.
    val productos: List<ProductoEntity>,    // Lista de productos incluidos.
    val total: Double,                      // Monto total del pedido.
    val fecha: Date = Date(),               // Fecha y hora en que se realizó el pedido.
    val direccion: String,                  // Dirección de envío ingresada por el usuario.
    val estado: String = "En proceso"       // Estado actual del pedido.
)

// ViewModel encargado de manejar la lógica del historial de pedidos.
// Se utiliza para crear y almacenar los pedidos realizados por el usuario.
class PedidoViewModel : ViewModel() {

    // Lista interna que almacena todos los pedidos realizados.
    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())

    // Versión pública y de solo lectura del listado de pedidos.
    val pedidos: StateFlow<List<Pedido>> = _pedidos.asStateFlow()

    // Función para crear un nuevo pedido.
    // Recibe los productos seleccionados y la dirección de envío del usuario.
    fun crearPedido(productos: List<ProductoEntity>, direccion: String) {
        viewModelScope.launch {
            // Si no hay productos, no se crea el pedido.
            if (productos.isEmpty()) return@launch

            // Calcula el total sumando los precios de los productos.
            val total = productos.sumOf { it.precio }

            // Genera un nuevo ID automáticamente (uno más que el último existente).
            val nuevoId = (_pedidos.value.maxOfOrNull { it.id } ?: 0) + 1

            // Crea un nuevo objeto Pedido con los datos ingresados.
            val nuevoPedido = Pedido(
                id = nuevoId,
                productos = productos,
                total = total,
                direccion = direccion
            )

            // Agrega el nuevo pedido a la lista existente.
            _pedidos.update { it + nuevoPedido }
        }
    }
}
