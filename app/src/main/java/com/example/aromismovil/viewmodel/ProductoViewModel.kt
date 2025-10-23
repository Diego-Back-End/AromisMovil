package com.example.aromismovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aromismovil.model.Pedido
import com.example.aromismovil.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    private val _carrito = MutableStateFlow<List<Producto>>(emptyList())
    val carrito: StateFlow<List<Producto>> = _carrito.asStateFlow()

    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos.asStateFlow()

    // ----------------- CRUD Productos -----------------
    fun agregarProducto(producto: Producto) {
        // genera id si viene 0
        val nextId = (_productos.value.maxOfOrNull { it.id } ?: 0) + 1
        _productos.update { it + producto.copy(id = if (producto.id == 0) nextId else producto.id) }
    }

    fun actualizarProducto(productoActualizado: Producto) {
        _productos.update { list ->
            list.map { if (it.id == productoActualizado.id) productoActualizado else it }
        }
    }

    fun eliminarProducto(id: Int) {
        _productos.update { it.filterNot { p -> p.id == id } }
    }

    // ----------------- Carrito -----------------
    fun agregarAlCarrito(producto: Producto) {
        // Si no hay stock, no agregar
        if (producto.stock <= 0) return
        _carrito.update { it + producto }
    }

    fun eliminarDelCarrito(id: Int) {
        _carrito.update { current ->
            val idx = current.indexOfFirst { it.id == id }
            if (idx >= 0) current.toMutableList().apply { removeAt(idx) } else current
        }
    }

    fun limpiarCarrito() { _carrito.value = emptyList() }

    // ----------------- Pedidos -----------------
    fun crearPedido(direccionEnvio: String) {
        viewModelScope.launch {
            val items = _carrito.value
            if (items.isEmpty()) return@launch
            val total = items.sumOf { it.precio }
            val nuevoId = (_pedidos.value.maxOfOrNull { it.id } ?: 0) + 1

            // descuenta stock
            val stockMap = items.groupBy { it.id }.mapValues { it.value.size }
            _productos.update { prods ->
                prods.map { p ->
                    val qty = stockMap[p.id] ?: 0
                    if (qty > 0) {
                        val nuevoStock = (p.stock - qty).coerceAtLeast(0)
                        p.copy(stock = nuevoStock, disponible = nuevoStock > 0)
                    } else p
                }
            }

            _pedidos.update { it + Pedido(nuevoId, items, total, direccionEnvio = direccionEnvio) }
            limpiarCarrito()
        }
    }

    fun actualizarEstadoPedido(id: Int, nuevoEstado: String) {
        _pedidos.update { list -> list.map { if (it.id == id) it.copy(estado = nuevoEstado) else it } }
    }

    // ----------------- Inventario -----------------
    fun actualizarStock(id: Int, nuevoStock: Int) {
        _productos.update { list ->
            list.map { if (it.id == id) it.copy(stock = nuevoStock, disponible = nuevoStock > 0) else it }
        }
    }

    fun obtenerProductoPorId(id: Int): Producto? = _productos.value.find { it.id == id }

    // Utilidad para precargar demo
    fun seed(productosDemo: List<Producto>) { productosDemo.forEach { agregarProducto(it) } }
}
