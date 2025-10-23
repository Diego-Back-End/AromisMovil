package com.example.aromismovil.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aromismovil.viewmodel.UsuarioViewModel

@Composable
fun ConfirmacionScreen(navController: NavHostController, usuarioViewModel: UsuarioViewModel) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¡Compra realizada con éxito!", style = MaterialTheme.typography.headlineMedium)
        Text("Gracias por tu compra.")
    }
}
