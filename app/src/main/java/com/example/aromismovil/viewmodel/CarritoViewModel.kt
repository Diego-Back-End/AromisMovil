package com.example.aromismovil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.aromismovil.model.ProductoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CarritoViewModel : ViewModel() {
    private val _carrito = MutableStateFlow<List<ProductoEntity>>(emptyList())
    val carrito: StateFlow<List<ProductoEntity>> = _carrito.asStateFlow()

    fun agregarAlCarrito(producto: ProductoEntity) { _carrito.update { it + producto } }
    fun eliminarDelCarrito(id: Int) { _carrito.update { it.filterNot { p -> p.id == id } } }
    fun limpiarCarrito() { _carrito.value = emptyList() }
}
