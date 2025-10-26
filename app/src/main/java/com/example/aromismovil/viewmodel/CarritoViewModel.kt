package com.example.aromismovil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.aromismovil.model.ProductoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ViewModel encargado de manejar la lógica del carrito de compras.
// Se encarga de agregar, eliminar y limpiar productos del carrito.
class CarritoViewModel : ViewModel() {

    // Estado interno del carrito, que contiene una lista de productos.
    // MutableStateFlow permite mantener los datos reactivos y observar los cambios.
    private val _carrito = MutableStateFlow<List<ProductoEntity>>(emptyList())

    // Versión pública del carrito, solo de lectura, para evitar modificaciones directas.
    val carrito: StateFlow<List<ProductoEntity>> = _carrito.asStateFlow()

    // Agrega un producto nuevo al carrito.
    // El operador "+" añade el producto al final de la lista actual.
    fun agregarAlCarrito(producto: ProductoEntity) {
        _carrito.update { it + producto }
    }

    // Elimina un producto del carrito según su ID.
    // Usa filterNot para crear una nueva lista sin el producto indicado.
    fun eliminarDelCarrito(id: Int) {
        _carrito.update { it.filterNot { p -> p.id == id } }
    }

    // Vacía completamente el carrito (por ejemplo, después de confirmar una compra).
    fun limpiarCarrito() {
        _carrito.value = emptyList()
    }
}
