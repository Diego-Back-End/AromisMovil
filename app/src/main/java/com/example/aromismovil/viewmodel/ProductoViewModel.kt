package com.example.aromismovil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.aromismovil.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProductoViewModel : ViewModel() {
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _carrito = MutableStateFlow<List<Producto>>(emptyList())
    val carrito: StateFlow<List<Producto>> = _carrito

    fun agregarProducto(producto: Producto) {
        _productos.update { it + producto }
    }

    fun eliminarProducto(id: Int) {
        _productos.update { it.filterNot { p -> p.id == id } }
    }

    fun agregarAlCarrito(producto: Producto) {
        _carrito.update { it + producto }
    }

    fun eliminarDelCarrito(id: Int) {
        _carrito.update { it.filterNot { p -> p.id == id } }
    }

    fun limpiarCarrito() {
        _carrito.value = emptyList()
    }
}
