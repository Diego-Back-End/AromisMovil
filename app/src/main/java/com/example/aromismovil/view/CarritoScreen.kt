package com.example.aromismovil.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aromismovil.viewmodel.ProductoViewModel
import com.example.aromismovil.viewmodel.UsuarioViewModel

@Composable
fun CarritoScreen(navController: NavController, viewModel: ProductoViewModel, usuarioViewModel: UsuarioViewModel) {
    val carrito by viewModel.carrito.collectAsState()
    val usuario by usuarioViewModel.usuario.collectAsState()
    val total = carrito.sumOf { it.precio }

    Column(Modifier.padding(16.dp)) {
        Text("Tu carrito", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        if (carrito.isEmpty()) {
            Text("El carrito está vacío")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(carrito) { p ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(p.nombre)
                        Row {
                            Text("$${"%.0f".format(p.precio)}")
                            Spacer(Modifier.width(12.dp))
                            TextButton(onClick = { viewModel.eliminarDelCarrito(p.id) }) { Text("Quitar") }
                        }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Text("Total: $${"%.0f".format(total)}")
            Spacer(Modifier.height(12.dp))

            if (usuario.direccion.isBlank()) {
                Text("Agrega una dirección en tu perfil antes de confirmar.", color = MaterialTheme.colorScheme.error)
                Button(onClick = { navController.navigate("perfil") }) { Text("Ir a Perfil") }
            } else {
                Button(onClick = {
                    viewModel.crearPedido(usuario.direccion)
                    navController.navigate("confirmacion")
                }) { Text("Confirmar compra") }
            }
        }
    }
}
