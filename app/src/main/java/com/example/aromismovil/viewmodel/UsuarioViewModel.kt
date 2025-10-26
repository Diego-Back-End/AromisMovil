package com.example.aromismovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aromismovil.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel encargado de manejar los datos y acciones relacionadas con el usuario.
// Controla el inicio y cierre de sesión, la actualización de datos personales y el rol del usuario.
class UsuarioViewModel : ViewModel() {

    // Estado interno del usuario actual.
    // MutableStateFlow permite que la interfaz se actualice automáticamente cuando cambian los datos.
    private val _usuario = MutableStateFlow(Usuario())

    // Versión pública del usuario (solo lectura) para evitar modificaciones directas.
    val usuario: StateFlow<Usuario> = _usuario.asStateFlow()

    // Variable que indica si el usuario tiene rol de administrador.
    private val _esAdministrador = MutableStateFlow(false)
    val esAdministrador: StateFlow<Boolean> = _esAdministrador.asStateFlow()

    // Función para iniciar sesión.
    // Recibe nombre, correo y rol, y actualiza el estado del usuario.
    fun iniciarSesion(nombre: String, correo: String, rol: String = "Cliente") {
        viewModelScope.launch {
            _usuario.value = Usuario(nombre = nombre, correo = correo, rol = rol)
            _esAdministrador.value = rol.equals("Administrador", ignoreCase = true)
        }
    }

    // Actualiza la dirección del usuario.
    fun actualizarDireccion(nueva: String) {
        _usuario.value = _usuario.value.copy(direccion = nueva)
    }

    // Actualiza el teléfono del usuario.
    fun actualizarTelefono(nuevo: String) {
        _usuario.value = _usuario.value.copy(telefono = nuevo)
    }

    // Cierra la sesión, restableciendo los valores del usuario y el rol.
    fun cerrarSesion() {
        _usuario.value = Usuario()
        _esAdministrador.value = false
    }

    // Cambia el rol del usuario (por ejemplo, de Cliente a Administrador).
    fun cambiarRol(rol: String) {
        _usuario.value = _usuario.value.copy(rol = rol)
        _esAdministrador.value = rol.equals("Administrador", ignoreCase = true)
    }
}
