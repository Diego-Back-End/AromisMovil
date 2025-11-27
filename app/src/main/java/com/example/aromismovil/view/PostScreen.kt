package com.example.aromismovil.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aromismovil.viewmodel.PostViewModel

@Composable
fun PostScreen(
    postViewModel: PostViewModel
) {
    val posts by postViewModel.posts.collectAsState()
    val isLoading by postViewModel.isLoading.collectAsState()
    val error by postViewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Productos de LookStore",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator()
            }

            error != null -> {
                Text(text = error ?: "Error desconocido")
            }

            else -> {
                LazyColumn {
                    items(posts) { producto ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(
                                text = producto.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Precio: $${producto.price}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = producto.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}
