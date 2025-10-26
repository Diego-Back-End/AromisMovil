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
import com.example.aromismovil.viewmodel.PedidoViewModel
import com.example.aromismovil.viewmodel.UsuarioViewModel

// Esta función representa la pantalla del carrito de compras.
// Muestra los productos agregados, el total y permite confirmar la compra.
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel,
    pedidoViewModel: PedidoViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    // Se obtiene la lista actual del carrito desde el ViewModel.
    val carrito by carritoViewModel.carrito.collectAsState()

    // Se obtiene la información del usuario (para saber su dirección, por ejemplo).
    val usuario by usuarioViewModel.usuario.collectAsState()

    // Se calcula el total sumando los precios de los productos en el carrito.
    val total = carrito.sumOf { it.precio }

    // Estructura principal de la pantalla.
    Column(Modifier.padding(16.dp)) {
        Text("Tu carrito", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        // Si el carrito está vacío, se muestra un mensaje.
        if (carrito.isEmpty()) {
            Text("El carrito está vacío")
        } else {
            // Si hay productos, se muestran en una lista vertical (LazyColumn).
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(carrito) { producto ->
                    // Cada producto se muestra en una fila con su nombre, precio y botón de eliminar.
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(producto.nombre)
                        Row {
                            Text("$${"%.0f".format(producto.precio)}")
                            Spacer(Modifier.width(12.dp))
                            // Botón para quitar un producto del carrito.
                            TextButton(onClick = {
                                carritoViewModel.eliminarDelCarrito(producto.id)
                            }) { Text("Quitar") }
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            // Se muestra el total del carrito.
            Text("Total: $${"%.0f".format(total)}")
            Spacer(Modifier.height(12.dp))

            // Si el usuario no tiene dirección registrada, se le pide agregar una.
            if (usuario.direccion.isBlank()) {
                Text(
                    "Agrega una dirección en tu perfil antes de confirmar.",
                    color = MaterialTheme.colorScheme.error
                )
                Button(onClick = { navController.navigate("perfil") }) {
                    Text("Ir a Perfil")
                }
            } else {
                // Si tiene dirección, puede confirmar la compra.
                Button(onClick = {
                    // Se crea un pedido con los datos del carrito y la dirección del usuario.
                    pedidoViewModel.crearPedido(carrito, usuario.direccion)
                    // Se limpia el carrito después de confirmar.
                    carritoViewModel.limpiarCarrito()
                    // Navega a la pantalla de confirmación.
                    navController.navigate("confirmacion")
                }) {
                    Text("Confirmar compra")
                }
            }
        }
    }
}
