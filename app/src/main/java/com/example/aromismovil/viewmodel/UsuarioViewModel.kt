package com.example.aromismovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.aromismovil.model.Usuario

class UsuarioViewModel : ViewModel() {

    // Estado del usuario actual
    private val _usuario = MutableStateFlow(Usuario())
    val usuario: StateFlow<Usuario> = _usuario

    // Simula iniciar sesión
    fun iniciarSesion(nombre: String, correo: String) {
        viewModelScope.launch {
            _usuario.value = Usuario(nombre = nombre, correo = correo, direccion = "")
        }
    }

    // Actualiza los datos del usuario
    fun actualizarDireccion(nuevaDireccion: String) {
        viewModelScope.launch {
            val actual = _usuario.value
            _usuario.value = actual.copy(direccion = nuevaDireccion)
        }
    }

    // Cierra sesión
    fun cerrarSesion() {
        viewModelScope.launch {
            _usuario.value = Usuario() // Reinicia los datos
        }
    }
}
