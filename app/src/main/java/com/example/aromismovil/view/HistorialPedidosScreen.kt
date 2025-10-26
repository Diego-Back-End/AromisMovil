package com.example.aromismovil.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aromismovil.viewmodel.PedidoViewModel
import java.text.SimpleDateFormat
import java.util.*

// Pantalla que muestra el historial de pedidos realizados por el usuario.
// Permite revisar las fechas, totales y estados de los pedidos anteriores.
@Composable
fun HistorialPedidosScreen(navController: NavController, pedidoViewModel: PedidoViewModel) {

    // Se obtiene la lista de pedidos desde el ViewModel en tiempo real.
    val pedidos by pedidoViewModel.pedidos.collectAsState()

    // Se define el formato que se usará para mostrar las fechas de los pedidos.
    val fmt = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }

    // Contenedor principal de la pantalla.
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título principal.
        Text(
            "Historial de Pedidos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Si no hay pedidos registrados, se muestra un mensaje al centro de la pantalla.
        if (pedidos.isEmpty()) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text("No hay pedidos registrados") }

            // Si existen pedidos, se muestran en una lista vertical.
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(pedidos) { ped ->
                    // Cada pedido se muestra dentro de una tarjeta (Card).
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {

                            // Fila con el número de pedido y su fecha formateada.
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Pedido #${ped.id}", style = MaterialTheme.typography.titleMedium)
                                Text(fmt.format(ped.fecha))
                            }

                            Spacer(Modifier.height(8.dp))

                            // Cantidad de productos dentro del pedido.
                            Text("Productos: ${ped.productos.size}")

                            // Total pagado en el pedido.
                            Text("Total: $${"%.0f".format(ped.total)}")

                            Spacer(Modifier.height(8.dp))

                            // Estado actual del pedido (pendiente, enviado, entregado, etc.).
                            Text("Estado: ${ped.estado}")
                        }
                    }
                }
            }
        }
    }
}
