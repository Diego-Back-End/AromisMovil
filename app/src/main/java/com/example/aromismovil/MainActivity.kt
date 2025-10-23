package com.example.aromismovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aromismovil.model.Producto
import com.example.aromismovil.ui.theme.AromisMovilTheme
import com.example.aromismovil.view.*
import com.example.aromismovil.viewmodel.ProductoViewModel
import com.example.aromismovil.viewmodel.UsuarioViewModel
import androidx.compose.runtime.getValue
class MainActivity : ComponentActivity() {

    private val productoViewModel: ProductoViewModel by viewModels()
    private val usuarioViewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val demo = listOf(
            Producto(
                id = 1,
                nombre = "Polera Oversize Blanca",
                precio = 12990.0,
                descripcion = "Polera 100% algodón con corte oversize",
                imagenRes = R.drawable.ropa3, //  tu imagen local
                stock = 10
            ),
            Producto(
                id = 2,
                nombre = "Pantalón Cargo Beige",
                precio = 19990.0,
                descripcion = "Pantalón con bolsillos laterales",
                imagenRes = R.drawable.ropa2,
                stock = 5
            ),
            Producto(
                id = 3,
                nombre = "Chaqueta Jeans Unisex",
                precio = 24990.0,
                descripcion = "Chaqueta de mezclilla clásica",
                imagenRes = R.drawable.ropa1,
                stock = 3
            )
        )


        // Carga los productos solo si la lista está vacía
        if (productoViewModel.productos.value.isEmpty()) {
            productoViewModel.seed(demo)
        }

        setContent {
            AromisMovilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AromisApp(
                        productoViewModel = productoViewModel,
                        usuarioViewModel = usuarioViewModel
                    )
                }
            }
        }
    }
}

@Composable
private fun AromisApp(
    productoViewModel: ProductoViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    val navController: NavHostController = rememberNavController()
    val esAdmin by usuarioViewModel.esAdministrador.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                navController = navController,
                usuarioViewModel = usuarioViewModel
            )
        }

        composable("catalogo") {
            CatalogoScreen(
                navController = navController,
                viewModel = productoViewModel
            )
        }

        composable("carrito") {
            CarritoScreen(
                navController = navController,
                viewModel = productoViewModel,
                usuarioViewModel = usuarioViewModel
            )
        }

        composable("confirmacion") {
            ConfirmacionScreen(
                navController = navController,
                usuarioViewModel = usuarioViewModel
            )
        }

        composable("perfil") {
            PerfilScreen(
                viewModel = usuarioViewModel,
                navController = navController
            )
        }

        composable("historial") {
            HistorialPedidosScreen(
                navController = navController,
                viewModel = productoViewModel
            )
        }

        composable("gestion") {
            if (esAdmin) {
                GestionProductosScreen(
                    navController = navController,
                    viewModel = productoViewModel
                )
            } else {
                navController.navigate("catalogo")
            }
        }

        // 🔹 Detalle del producto con validación segura
        composable("detalle/{productoId}") { backStack ->
            val id = backStack.arguments?.getString("productoId")?.toIntOrNull()
            val producto = id?.let { productoViewModel.obtenerProductoPorId(it) }

            if (producto != null) {
                DetalleProductoScreen(
                    producto = producto,
                    navController = navController,
                    viewModel = productoViewModel
                )
            } else {
                // Evita crash si el ID no existe
                navController.popBackStack()
            }
        }
    }
}
