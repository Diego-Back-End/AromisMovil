package com.example.aromismovil.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aromismovil.viewmodel.UsuarioViewModel

@Composable
fun PerfilScreen(viewModel: UsuarioViewModel) {
    val usuario by viewModel.usuario.collectAsState()

    Column(Modifier.padding(16.dp)) {
        Text("Nombre: ${usuario.nombre}")
        Text("Correo: ${usuario.correo}")
        Text("Dirección: ${usuario.direccion}")
    }
}
