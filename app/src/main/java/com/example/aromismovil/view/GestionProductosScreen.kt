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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aromismovil.model.ProductoEntity
import com.example.aromismovil.viewmodel.ProductoViewModel

// Pantalla que permite al administrador gestionar los productos del sistema.
// Desde aquí se pueden agregar, editar o eliminar productos del catálogo.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionProductosScreen(navController: NavController, viewModel: ProductoViewModel) {

    // Se obtiene la lista de productos actual desde el ViewModel.
    val productos by viewModel.productos.collectAsState()

    // Variables para controlar si se muestra el diálogo y qué producto se está editando.
    var mostrarDialogo by remember { mutableStateOf(false) }
    var productoEditar by remember { mutableStateOf<ProductoEntity?>(null) }

    // Estructura principal con barra superior y botón flotante.
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Gestión de Productos") }) },

        // Botón flotante para abrir el formulario de agregar un nuevo producto.
        floatingActionButton = {
            FloatingActionButton(onClick = {
                productoEditar = null // Si no hay producto seleccionado, será uno nuevo.
                mostrarDialogo = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Si no hay productos, se muestra un mensaje informativo.
            if (productos.isEmpty()) {
                Text("No hay productos registrados")
            } else {
                // Lista de productos existentes.
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(productos) { p ->
                        // Cada producto se muestra dentro de una tarjeta.
                        Card(Modifier.fillMaxWidth()) {
                            ListItem(
                                headlineContent = { Text(p.nombre, fontWeight = FontWeight.Bold) },
                                supportingContent = {
                                    Text("$${"%.0f".format(p.precio)} - Stock: ${p.stock}")
                                },
                                // Acciones para editar o eliminar cada producto.
                                trailingContent = {
                                    Row {
                                        // Botón para editar un producto existente.
                                        IconButton(onClick = {
                                            productoEditar = p
                                            mostrarDialogo = true
                                        }) {
                                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                                        }

                                        // Botón para eliminar el producto.
                                        IconButton(onClick = {
                                            viewModel.eliminarProducto(p.id)
                                        }) {
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

        // Diálogo que se muestra cuando se agrega o edita un producto.
        if (mostrarDialogo) {
            DialogoProducto(
                producto = productoEditar,
                onConfirmar = { nuevo ->
                    // Si el producto es nuevo, se agrega; si ya existía, se actualiza.
                    if (productoEditar == null)
                        viewModel.agregarProducto(nuevo)
                    else
                        viewModel.actualizarProducto(nuevo)
                    mostrarDialogo = false
                    productoEditar = null
                },
                onCancelar = {
                    mostrarDialogo = false
                    productoEditar = null
                }
            )
        }
    }
}

// Componente que representa el cuadro de diálogo para agregar o editar un producto.
@Composable
private fun DialogoProducto(
    producto: ProductoEntity?,                      // Producto actual o nulo si es nuevo.
    onConfirmar: (ProductoEntity) -> Unit,          // Acción al confirmar.
    onCancelar: () -> Unit                          // Acción al cancelar.
) {
    val context = LocalContext.current

    // Campos del formulario (se llenan si es edición, quedan vacíos si es nuevo).
    var nombre by remember { mutableStateOf(producto?.nombre ?: "") }
    var precioTxt by remember { mutableStateOf(producto?.precio?.toString() ?: "") }
    var stockTxt by remember { mutableStateOf(producto?.stock?.toString() ?: "") }
    var descripcion by remember { mutableStateOf(producto?.descripcion ?: "") }
    var imagenNombre by remember { mutableStateOf("") }

    // Validaciones básicas del formulario.
    val precioOk = precioTxt.toDoubleOrNull()?.let { it >= 0 } == true
    val stockOk = stockTxt.toIntOrNull()?.let { it >= 0 } == true
    val nombreOk = nombre.isNotBlank()
    val formOk = precioOk && stockOk && nombreOk

    // Diálogo principal.
    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text(if (producto == null) "Agregar Producto" else "Editar Producto") },
        text = {
            Column {
                // Campo de texto: nombre del producto.
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    isError = !nombreOk,
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                // Campo de texto: precio del producto.
                OutlinedTextField(
                    value = precioTxt,
                    onValueChange = { precioTxt = it },
                    isError = !precioOk,
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                // Campo de texto: stock disponible.
                OutlinedTextField(
                    value = stockTxt,
                    onValueChange = { stockTxt = it },
                    isError = !stockOk,
                    label = { Text("Stock") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                // Campo de texto: descripción del producto.
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                // Campo de texto: nombre del recurso de imagen (sin la extensión .png).
                OutlinedTextField(
                    value = imagenNombre,
                    onValueChange = { imagenNombre = it },
                    label = { Text("Nombre de imagen (sin .png)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        // Botón de confirmar (agregar o editar el producto).
        confirmButton = {
            Button(
                onClick = {
                    // Busca la imagen en los recursos usando el nombre ingresado.
                    val resId = context.resources.getIdentifier(
                        imagenNombre,
                        "drawable",
                        context.packageName
                    )

                    // Crea un nuevo objeto ProductoEntity con los datos ingresados.
                    val nuevoProducto = ProductoEntity(
                        id = producto?.id ?: 0,
                        nombre = nombre,
                        precio = precioTxt.toDoubleOrNull() ?: 0.0,
                        stock = stockTxt.toIntOrNull() ?: 0,
                        descripcion = descripcion,
                        imagenRes = if (resId != 0) resId else producto?.imagenRes ?: 0
                    )

                    // Llama a la función de confirmación con el nuevo producto.
                    onConfirmar(nuevoProducto)
                },
                enabled = formOk // Solo se habilita si el formulario es válido.
            ) {
                Text("Confirmar")
            }
        },
        // Botón de cancelar.
        dismissButton = {
            TextButton(onClick = onCancelar) { Text("Cancelar") }
        }
    )
}
