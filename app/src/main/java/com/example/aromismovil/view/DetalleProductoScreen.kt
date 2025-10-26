package com.example.aromismovil.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aromismovil.model.ProductoEntity
import com.example.aromismovil.viewmodel.CarritoViewModel
import com.example.aromismovil.viewmodel.ProductoViewModel

// Pantalla que muestra la información detallada de un producto seleccionado del catálogo.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    producto: ProductoEntity,               // Producto que se mostrará en detalle.
    navController: NavController,           // Controlador de navegación para volver o ir al carrito.
    productoViewModel: ProductoViewModel,   // ViewModel que maneja los datos del producto.
    carritoViewModel: CarritoViewModel      // ViewModel que maneja el carrito de compras.
) {
    val scroll = rememberScrollState() // Permite hacer scroll cuando el contenido es largo.

    // Estructura general de la pantalla con barra superior y botón flotante.
    Scaffold(
        // Barra superior con el título y el botón de volver atrás.
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        // Botón flotante para agregar el producto al carrito.
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    carritoViewModel.agregarAlCarrito(producto)
                    navController.navigate("carrito")
                }
            ) {
                Text("Agregar - $${"%.0f".format(producto.precio)}")
            }
        }
    ) { inner ->
        // Contenido principal desplazable de la pantalla.
        Column(
            modifier = Modifier
                .padding(inner)
                .verticalScroll(scroll)
                .fillMaxSize()
        ) {
            // Sección de la imagen del producto.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentAlignment = Alignment.Center
            ) {
                // Si el producto tiene imagen, se muestra; si no, se muestra un texto de aviso.
                if (producto.imagenRes != 0) {
                    Image(
                        painter = painterResource(id = producto.imagenRes),
                        contentDescription = producto.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        "Sin imagen disponible",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            // Sección con los detalles del producto (nombre, precio, descripción y stock).
            Column(Modifier.padding(16.dp)) {
                // Nombre del producto.
                Text(
                    producto.nombre,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(6.dp))

                // Precio del producto en formato numérico.
                Text(
                    "$${"%.0f".format(producto.precio)}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(12.dp))

                // Descripción detallada.
                Text("Descripción", style = MaterialTheme.typography.titleMedium)
                Text(producto.descripcion)

                Spacer(Modifier.height(12.dp))

                // Mostrar si el producto está disponible o agotado según el stock.
                Text(
                    text = if (producto.stock > 0)
                        "Disponibilidad: En stock (${producto.stock})"
                    else
                        "Disponibilidad: Agotado"
                )
            }
        }
    }
}
