package com.example.aromismovil.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aromismovil.viewmodel.UsuarioViewModel

// Pantalla de inicio de sesión de la aplicación.
// Permite ingresar el nombre, correo y rol del usuario para acceder al sistema.
@Composable
fun LoginScreen(navController: NavController, usuarioViewModel: UsuarioViewModel) {

    // Variables que almacenan los datos ingresados por el usuario.
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("Cliente") }

    // Estructura principal de la pantalla.
    Column(
        modifier = Modifier
            .fillMaxSize()                  // Ocupa toda la pantalla.
            .padding(32.dp),                // Agrega márgenes internos.
        horizontalAlignment = Alignment.CenterHorizontally,  // Centra los elementos horizontalmente.
        verticalArrangement = Arrangement.Center             // Centra el contenido verticalmente.
    ) {
        // Título de la pantalla (nombre de la app).
        Text("LookStore", style = MaterialTheme.typography.headlineLarge)

        Spacer(Modifier.height(24.dp))

        // Campo de texto para ingresar el nombre del usuario.
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Campo de texto para ingresar el correo electrónico.
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Campo de texto para definir el rol (Cliente o Administrador).
        OutlinedTextField(
            value = rol,
            onValueChange = { rol = it },
            label = { Text("Rol (Cliente/Administrador)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        // Botón para iniciar sesión.
        Button(
            onClick = {
                // Guarda los datos del usuario en el ViewModel.
                usuarioViewModel.iniciarSesion(nombre, correo, rol)

                // Si el rol es "Administrador", va a la pantalla de gestión.
                // Si es "Cliente", va al catálogo de productos.
                if (rol.equals("Administrador", ignoreCase = true))
                    navController.navigate("gestion")
                else
                    navController.navigate("catalogo")
            },
            // Solo se habilita el botón si el nombre y correo no están vacíos.
            enabled = nombre.isNotBlank() && correo.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }
    }
}
