package com.example.aromismovil.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aromismovil.viewmodel.UsuarioViewModel

@Composable
fun LoginScreen(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("Cliente") }

    Column(
        Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Aromis Moda", style = MaterialTheme.typography.headlineLarge)

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(correo, { correo = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(rol, { rol = it }, label = { Text("Rol (Cliente/Administrador)") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                usuarioViewModel.iniciarSesion(nombre, correo, rol)
                if (rol.equals("Administrador", ignoreCase = true)) navController.navigate("gestion")
                else navController.navigate("catalogo")
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Iniciar Sesión") }
    }
}
