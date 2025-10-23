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

@Composable
fun CatalogoScreen(navController: NavController, viewModel: ProductoViewModel) {
    val productos by viewModel.productos.collectAsState()

    LazyColumn(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(productos) { p ->
            Card(
                onClick = { navController.navigate("detalle/${p.id}") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(p.nombre, style = MaterialTheme.typography.titleMedium)
                    Text("Precio: $${"%.0f".format(p.precio)}")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.agregarAlCarrito(p) }) { Text("Agregar al carrito") }
                }
            }
        }
    }
}
