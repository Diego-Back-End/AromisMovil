package com.example.aromismovil.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

    val usuario by viewModel.usuario.collectAsState()
    val esAdmin by viewModel.esAdministrador.collectAsState()

    var editar by remember { mutableStateOf(false) }

    var direccion by remember { mutableStateOf(usuario.direccion) }
    var telefono by remember { mutableStateOf(usuario.telefono) }

    Column(Modifier.padding(16.dp)) {

        Text("Perfil de Usuario", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        Text("Nombre: ${usuario.nombre}")
        Text("Correo: ${usuario.correo}")
        Text("Rol: ${usuario.rol}")

        Spacer(Modifier.height(12.dp))

        if (editar) {

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Row {
                Button(onClick = {
                    viewModel.actualizarDireccion(direccion)
                    viewModel.actualizarTelefono(telefono)
                    editar = false
                }) { Text("Guardar") }

                Spacer(Modifier.width(8.dp))

                TextButton(onClick = { editar = false }) { Text("Cancelar") }
            }

        } else {

            Text("Dirección: ${usuario.direccion.ifBlank { "—" }}")
            Text("Teléfono: ${usuario.telefono.ifBlank { "—" }}")

            Spacer(Modifier.height(12.dp))

            Button(onClick = { editar = true }) { Text("Editar Perfil") }

            Spacer(Modifier.height(8.dp))

            Button(onClick = { navController.navigate("historial") }) {
                Text("Ver Historial de Pedidos")
            }

            if (esAdmin) {
                Spacer(Modifier.height(8.dp))
                Button(onClick = { navController.navigate("gestion") }) {
                    Text("Gestión de Productos")
                }
            }

            Spacer(Modifier.height(16.dp))

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

        // 👇👇 PERIFÉRICO: GALERÍA
        Spacer(Modifier.height(24.dp))
        GaleriaSection()   // se define en otro archivo del mismo package
    }
}
