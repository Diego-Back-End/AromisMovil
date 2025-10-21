package com.example.aromismovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            AromisApp(productoViewModel, usuarioViewModel)
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
