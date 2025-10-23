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
import com.example.aromismovil.model.Producto
import com.example.aromismovil.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    producto: Producto,
    navController: NavController,
    viewModel: ProductoViewModel
) {
    val scroll = rememberScrollState()

    Scaffold(
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
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.agregarAlCarrito(producto)
                    navController.navigate("carrito")
                }
            ) {
                Text("Agregar - $${"%.0f".format(producto.precio)}")
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .verticalScroll(scroll)
                .fillMaxSize()
        ) {
            // ✅ Imagen local desde drawable
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentAlignment = Alignment.Center
            ) {
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

            // 🧾 Detalle del producto
            Column(Modifier.padding(16.dp)) {
                Text(
                    producto.nombre,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    "$${"%.0f".format(producto.precio)}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(12.dp))

                Text("Descripción", style = MaterialTheme.typography.titleMedium)
                Text(producto.descripcion)

                Spacer(Modifier.height(12.dp))

                Text(
                    "Disponibilidad: " +
                            if (producto.disponible) "En stock (${producto.stock})"
                            else "Agotado"
                )
            }
        }
    }
}
