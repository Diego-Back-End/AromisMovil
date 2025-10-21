package com.example.aromismovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aromismovil.model.Producto
import com.example.aromismovil.view.CatalogoScreen
import com.example.aromismovil.view.CarritoScreen
import com.example.aromismovil.view.ConfirmacionScreen
import com.example.aromismovil.view.PerfilScreen
import com.example.aromismovil.viewmodel.ProductoViewModel
import com.example.aromismovil.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {

    private val productoViewModel: ProductoViewModel by viewModels()
    private val usuarioViewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AromisApp(
                productoViewModel = productoViewModel,
                usuarioViewModel = usuarioViewModel
            )
        }
    }
}

@Composable
fun AromisApp(
    productoViewModel: ProductoViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    val navController: NavHostController = rememberNavController()

    MaterialTheme {
        Surface {
            NavHost(
                navController = navController,
                startDestination = "catalogo"
            ) {
                composable("catalogo") {
                    CatalogoScreen(
                        navController = navController,
                        viewModel = productoViewModel
                    )
                }
                composable("carrito") {
                    CarritoScreen(
                        navController = navController,
                        viewModel = productoViewModel
                    )
                }
                composable("confirmacion") {
                    ConfirmacionScreen(navController = navController)
                }
                composable("perfil") {
                    PerfilScreen(viewModel = usuarioViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AromisAppPreview() {
    // Creamos ViewModels falsos con datos de ejemplo
    val fakeProductoViewModel = ProductoViewModel().apply {
        agregarProducto(
            Producto(
                id = 1,
                nombre = "Polera Oversize Blanca",
                precio = 12990.0,
                descripcion = "Polera 100% algodón con corte oversize",
                imagenUrl = "https://example.com/polera.jpg"
            )
        )
        agregarProducto(
            Producto(
                id = 2,
                nombre = "Pantalón Cargo Beige",
                precio = 19990.0,
                descripcion = "Pantalón con bolsillos laterales y ajuste en tobillos",
                imagenUrl = "https://example.com/pantalon.jpg"
            )
        )
        agregarProducto(
            Producto(
                id = 3,
                nombre = "Chaqueta Jeans Unisex",
                precio = 24990.0,
                descripcion = "Chaqueta de mezclilla clásica con botones metálicos",
                imagenUrl = "https://example.com/chaqueta.jpg"
            )
        )
    }

    val fakeUsuarioViewModel = UsuarioViewModel().apply {
        iniciarSesion("Luka Núñez", "luka@aromis.cl")
        actualizarDireccion("Av. Providencia 1020, Santiago")
    }

    AromisApp(
        productoViewModel = fakeProductoViewModel,
        usuarioViewModel = fakeUsuarioViewModel
    )
}
