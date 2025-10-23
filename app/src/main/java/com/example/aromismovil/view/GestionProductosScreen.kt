package com.example.aromismovil.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aromismovil.model.Producto
import com.example.aromismovil.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionProductosScreen(navController: NavController, viewModel: ProductoViewModel) {
    val productos by viewModel.productos.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }
    var productoEditar by remember { mutableStateOf<Producto?>(null) }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Gestión de Productos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { productoEditar = null; mostrarDialogo = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { inner ->
        Column(Modifier.padding(inner).padding(16.dp).fillMaxSize()) {
            if (productos.isEmpty()) {
                Text("No hay productos registrados")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(productos) { p ->
                        Card(Modifier.fillMaxWidth()) {
                            ListItem(
                                headlineContent = { Text(p.nombre, fontWeight = FontWeight.Bold) },
                                supportingContent = { Text("$${"%.0f".format(p.precio)} - Stock: ${p.stock}") },
                                trailingContent = {
                                    Row {
                                        IconButton(onClick = { productoEditar = p; mostrarDialogo = true }) {
                                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                                        }
                                        IconButton(onClick = { viewModel.eliminarProducto(p.id) }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        if (mostrarDialogo) {
            DialogoProducto(
                producto = productoEditar,
                onConfirmar = { nuevo ->
                    if (productoEditar == null) viewModel.agregarProducto(nuevo)
                    else viewModel.actualizarProducto(nuevo)
                    mostrarDialogo = false; productoEditar = null
                },
                onCancelar = { mostrarDialogo = false; productoEditar = null }
            )
        }
    }
}

@Composable
private fun DialogoProducto(
    producto: Producto?,
    onConfirmar: (Producto) -> Unit,
    onCancelar: () -> Unit
) {
    var nombre by remember { mutableStateOf(producto?.nombre ?: "") }
    var precioTxt by remember { mutableStateOf(producto?.precio?.toString() ?: "") }
    var stockTxt by remember { mutableStateOf(producto?.stock?.toString() ?: "") }
    var descripcion by remember { mutableStateOf(producto?.descripcion ?: "") }
    var imagenUrl by remember { mutableStateOf(producto?.imagenUrl ?: "") }

    val precioOk = precioTxt.toDoubleOrNull()?.let { it >= 0 } == true
    val stockOk = stockTxt.toIntOrNull()?.let { it >= 0 } == true
    val nombreOk = nombre.isNotBlank()
    val formOk = precioOk && stockOk && nombreOk

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text(if (producto == null) "Agregar Producto" else "Editar Producto") },
        text = {
            Column {
                OutlinedTextField(nombre, { nombre = it }, isError = !nombreOk, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(precioTxt, { precioTxt = it }, isError = !precioOk, label = { Text("Precio") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(stockTxt, { stockTxt = it }, isError = !stockOk, label = { Text("Stock") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(descripcion, { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(imagenUrl, { imagenUrl = it }, label = { Text("URL de imagen") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val p = Producto(
                        id = producto?.id ?: 0,
                        nombre = nombre,
                        precio = precioTxt.toDoubleOrNull() ?: 0.0,
                        stock = stockTxt.toIntOrNull() ?: 0,
                        descripcion = descripcion,
                        imagenUrl = imagenUrl,
                        disponible = (stockTxt.toIntOrNull() ?: 0) > 0
                    )
                    onConfirmar(p)
                },
                enabled = formOk
            ) { Text("Confirmar") }
        },
        dismissButton = { TextButton(onClick = onCancelar) { Text("Cancelar") } }
    )
}
