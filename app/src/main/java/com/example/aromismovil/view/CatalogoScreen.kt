package com.example.aromismovil.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
fun CatalogoScreen(
    navController: NavController,
    viewModel: ProductoViewModel
) {
    val productos by viewModel.productos.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(productos) { producto ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate("detalle/${producto.id}") }
            ) {
                Column(Modifier.padding(8.dp)) {
                    Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                    Text("Precio: $${producto.precio}")
                    Button(onClick = { viewModel.agregarAlCarrito(producto) }) {
                        Text("Agregar al carrito")
                    }
                }
            }
        }
    }
}
