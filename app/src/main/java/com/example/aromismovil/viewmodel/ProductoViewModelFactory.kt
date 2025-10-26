package com.example.aromismovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aromismovil.repository.ProductoRepository

// Clase que se encarga de crear instancias del ProductoViewModel.
// Es necesaria porque el ViewModel recibe un parámetro (el repositorio),
// y no puede crearse directamente sin esta fábrica.
class ProductoViewModelFactory(private val repo: ProductoRepository) : ViewModelProvider.Factory {

    // Método que crea el ViewModel y le pasa el repositorio como dependencia.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductoViewModel(repo) as T
    }
}
