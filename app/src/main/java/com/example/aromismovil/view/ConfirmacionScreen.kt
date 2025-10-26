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

// Esta función representa la pantalla de confirmación de compra.
// Se muestra después de que el usuario confirma y completa su pedido.
@Composable
fun ConfirmacionScreen(navController: NavHostController, usuarioViewModel: UsuarioViewModel) {

    // Estructura principal de la pantalla.
    // Se usa un Column para centrar el contenido tanto vertical como horizontalmente.
    Column(
        Modifier
            .fillMaxSize()         // Ocupa toda la pantalla.
            .padding(16.dp),       // Agrega márgenes internos.
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Mensaje principal que confirma el éxito de la compra.
        Text(
            "¡Compra realizada con éxito!",
            style = MaterialTheme.typography.headlineMedium
        )

        // Mensaje secundario de agradecimiento al usuario.
        Text("Gracias por tu compra.")
    }
}
