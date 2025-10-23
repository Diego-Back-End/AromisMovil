package com.example.aromismovil.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aromismovil.viewmodel.CarritoViewModel
import com.example.aromismovil.viewmodel.ProductoViewModel

@Composable
fun CatalogoScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel
) {
    val productos by productoViewModel.productos.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Catálogo de productos",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(productos) { producto ->
                Card(
                    onClick = { navController.navigate("detalle/${producto.id}") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = producto.nombre,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text("Precio: $${"%.0f".format(producto.precio)}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                carritoViewModel.agregarAlCarrito(producto)
                            }
                        ) {
                            Text("Agregar al carrito")
                        }
                    }
                }
            }
        }
    }
}
