package com.example.aromismovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aromismovil.model.ProductoEntity
import com.example.aromismovil.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel(private val repo: ProductoRepository) : ViewModel() {
    val productos = repo.obtenerProductos().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun agregarProducto(p: ProductoEntity) = viewModelScope.launch { repo.insertar(p) }
    fun actualizarProducto(p: ProductoEntity) = viewModelScope.launch { repo.actualizar(p) }
    fun eliminarProducto(id: Int) = viewModelScope.launch { repo.eliminar(id) }
}
