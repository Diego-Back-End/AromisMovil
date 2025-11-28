package com.example.aromismovil.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun GaleriaSection() {

    // Uri de la imagen seleccionada
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador para abrir la galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri // guardamos la uri seleccionada
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Button(
            onClick = { launcher.launch("image/*") }
        ) {
            Text("Seleccionar imagen desde galería")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Si el usuario seleccionó una imagen, la mostramos
        imageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Imagen seleccionada",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
