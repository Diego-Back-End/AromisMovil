package com.example.aromismovil.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aromismovil.viewmodel.UsuarioViewModel

// Pantalla del perfil de usuario.
// Permite ver los datos personales, editarlos, acceder al historial de pedidos
// o ir a la gestión de productos si el usuario es administrador.
@Composable
fun PerfilScreen(viewModel: UsuarioViewModel, navController: NavHostController) {

    // Se obtiene la información actual del usuario desde el ViewModel.
    val usuario by viewModel.usuario.collectAsState()

    // Se detecta si el usuario tiene rol de administrador.
    val esAdmin by viewModel.esAdministrador.collectAsState()

    // Variables que controlan si el usuario está editando sus datos.
    var editar by remember { mutableStateOf(false) }

    // Campos editables del perfil (dirección y teléfono).
    var direccion by remember { mutableStateOf(usuario.direccion) }
    var telefono by remember { mutableStateOf(usuario.telefono) }

    // Estructura principal del diseño.
    Column(Modifier.padding(16.dp)) {

        // Título de la pantalla.
        Text("Perfil de Usuario", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        // Muestra los datos principales del usuario.
        Text("Nombre: ${usuario.nombre}")
        Text("Correo: ${usuario.correo}")
        Text("Rol: ${usuario.rol}")

        Spacer(Modifier.height(12.dp))

        // Si el usuario presiona “Editar Perfil”, aparecen los campos de edición.
        if (editar) {

            // Campo para modificar la dirección.
            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            // Campo para modificar el teléfono.
            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // Botones para guardar los cambios o cancelar la edición.
            Row {
                Button(onClick = {
                    // Se actualizan los datos del usuario en el ViewModel.
                    viewModel.actualizarDireccion(direccion)
                    viewModel.actualizarTelefono(telefono)
                    editar = false
                }) { Text("Guardar") }

                Spacer(Modifier.width(8.dp))

                TextButton(onClick = { editar = false }) { Text("Cancelar") }
            }

        } else {
            // Si no está en modo edición, muestra los datos actuales del perfil.
            Text("Dirección: ${usuario.direccion.ifBlank { "—" }}")
            Text("Teléfono: ${usuario.telefono.ifBlank { "—" }}")

            Spacer(Modifier.height(12.dp))

            // Botón para cambiar al modo de edición.
            Button(onClick = { editar = true }) { Text("Editar Perfil") }

            Spacer(Modifier.height(8.dp))

            // Botón para ir al historial de pedidos.
            Button(onClick = { navController.navigate("historial") }) { Text("Ver Historial de Pedidos") }

            // Si el usuario es administrador, muestra la opción para ir a gestión de productos.
            if (esAdmin) {
                Spacer(Modifier.height(8.dp))
                Button(onClick = { navController.navigate("gestion") }) { Text("Gestión de Productos") }
            }

            Spacer(Modifier.height(16.dp))

            // Botón para cerrar sesión y volver a la pantalla de login.
            Button(
                onClick = {
                    viewModel.cerrarSesion()
                    navController.navigate("login")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) { Text("Cerrar Sesión") }
        }
    }
}
