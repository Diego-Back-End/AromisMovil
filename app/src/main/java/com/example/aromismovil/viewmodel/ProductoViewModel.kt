package com.example.aromismovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aromismovil.model.ProductoEntity
import com.example.aromismovil.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// ViewModel encargado de manejar la lógica relacionada con los productos.
// Se comunica con el repositorio para obtener, agregar, actualizar o eliminar productos.
class ProductoViewModel(private val repo: ProductoRepository) : ViewModel() {

    // Flujo de datos reactivo que obtiene la lista de productos desde el repositorio.
    // stateIn mantiene los datos activos mientras haya observadores.
    val productos = repo.obtenerProductos().stateIn(
        viewModelScope,                      // Alcance del ViewModel (se mantiene mientras esté activo).
        SharingStarted.WhileSubscribed(),    // Empieza a emitir cuando alguien observe los datos.
        emptyList()                          // Valor inicial vacío.
    )

    // Agrega un nuevo producto a la base de datos.
    fun agregarProducto(p: ProductoEntity) = viewModelScope.launch {
        repo.insertar(p)
    }

    // Actualiza los datos de un producto existente (por ejemplo, precio o stock).
    fun actualizarProducto(p: ProductoEntity) = viewModelScope.launch {
        repo.actualizar(p)
    }

    // Elimina un producto según su ID.
    fun eliminarProducto(id: Int) = viewModelScope.launch {
        repo.eliminar(id)
    }
}
