package com.example.aromismovil.view

import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aromismovil.viewmodel.ProductoViewModel

@Composable
fun CarritoScreen(
    navController: NavController,
    viewModel: ProductoViewModel
) {
    val carrito by viewModel.carrito.collectAsState()
    val total = carrito.sumOf { it.precio }

    Column(Modifier.padding(16.dp)) {
        Text("Tu carrito", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(carrito) { producto ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(producto.nombre)
                    Text("$${producto.precio}")
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Text("Total: $${"%.2f".format(total)}")
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            viewModel.limpiarCarrito()
            navController.navigate("confirmacion")
        }) {
            Text("Confirmar compra")
        }
    }
}
