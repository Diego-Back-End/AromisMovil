package com.example.aromismovil.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

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