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

@Composable
fun HistorialPedidosScreen(navController: NavController, pedidoViewModel: PedidoViewModel) {
    val pedidos by pedidoViewModel.pedidos.collectAsState()
    val fmt = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Historial de Pedidos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (pedidos.isEmpty()) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text("No hay pedidos registrados") }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(pedidos) { ped ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Pedido #${ped.id}", style = MaterialTheme.typography.titleMedium)
                                Text(fmt.format(ped.fecha))
                            }
                            Spacer(Modifier.height(8.dp))
                            Text("Productos: ${ped.productos.size}")
                            Text("Total: $${"%.0f".format(ped.total)}")
                            Spacer(Modifier.height(8.dp))
                            Text("Estado: ${ped.estado}")
                        }
                    }
                }
            }
        }
    }
}
