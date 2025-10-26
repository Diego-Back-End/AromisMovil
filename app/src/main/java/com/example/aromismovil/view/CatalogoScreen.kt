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

// Esta función representa la pantalla del catálogo de productos.
// Aquí el usuario puede ver todos los productos disponibles y agregarlos al carrito.
@Composable
fun CatalogoScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel
) {
    // Se obtiene la lista de productos desde el ViewModel.
    // "collectAsState" permite actualizar la pantalla automáticamente si cambian los datos.
    val productos by productoViewModel.productos.collectAsState()

    // Estructura principal de la pantalla.
    Column(modifier = Modifier.padding(16.dp)) {
        // Título principal del catálogo.
        Text(
            text = "Catálogo de productos",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Lista vertical que muestra todos los productos (similar a un RecyclerView).
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(productos) { producto ->
                // Cada producto se muestra dentro de una tarjeta (Card).
                Card(
                    // Al hacer clic en la tarjeta, se navega a la pantalla de detalle del producto.
                    onClick = { navController.navigate("detalle/${producto.id}") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // Nombre del producto.
                        Text(
                            text = producto.nombre,
                            style = MaterialTheme.typography.titleMedium
                        )

                        // Precio formateado en pesos chilenos sin decimales.
                        Text("Precio: $${"%.0f".format(producto.precio)}")

                        Spacer(modifier = Modifier.height(8.dp))

                        // Botón para agregar el producto al carrito.
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
