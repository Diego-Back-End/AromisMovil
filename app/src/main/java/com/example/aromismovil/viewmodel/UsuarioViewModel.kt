package com.example.aromismovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aromismovil.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val _usuario = MutableStateFlow(Usuario())
    val usuario: StateFlow<Usuario> = _usuario.asStateFlow()

    private val _esAdministrador = MutableStateFlow(false)
    val esAdministrador: StateFlow<Boolean> = _esAdministrador.asStateFlow()

    fun iniciarSesion(nombre: String, correo: String, rol: String = "Cliente") {
        viewModelScope.launch {
            _usuario.value = Usuario(nombre = nombre, correo = correo, rol = rol)
            _esAdministrador.value = rol.equals("Administrador", ignoreCase = true)
        }
    }

    fun actualizarDireccion(nueva: String) {
        _usuario.value = _usuario.value.copy(direccion = nueva)
    }

    fun actualizarTelefono(nuevo: String) {
        _usuario.value = _usuario.value.copy(telefono = nuevo)
    }

    fun cerrarSesion() {
        _usuario.value = Usuario()
        _esAdministrador.value = false
    }

    fun cambiarRol(rol: String) {
        _usuario.value = _usuario.value.copy(rol = rol)
        _esAdministrador.value = rol.equals("Administrador", ignoreCase = true)
    }
}
