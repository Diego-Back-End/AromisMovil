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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aromismovil.model.AppDatabase
import com.example.aromismovil.model.ProductoEntity
import com.example.aromismovil.repository.ProductoRepository
import com.example.aromismovil.ui.theme.AromisMovilTheme
import com.example.aromismovil.view.*
import com.example.aromismovil.viewmodel.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var productoViewModel: ProductoViewModel
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val carritoViewModel: CarritoViewModel by viewModels()
    private val pedidoViewModel: PedidoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  Inicializar base de datos y repositorio
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ProductoRepository(database.productoDao())

        //  Crear ViewModel de productos con su Factory
        val factory = ProductoViewModelFactory(repository)
        productoViewModel = ViewModelProvider(this, factory)[ProductoViewModel::class.java]

        //  Lista de productos de ejemplo
        val demo = listOf(
            ProductoEntity(
                id = 1,
                nombre = "Polera Oversize Blanca",
                precio = 12990.0,
                descripcion = "Polera 100% algodón con corte oversize",
                imagenRes = R.drawable.ropa1,
                stock = 10
            ),
            ProductoEntity(
                id = 2,
                nombre = "Pantalón Cargo Beige",
                precio = 19990.0,
                descripcion = "Pantalón con bolsillos laterales y ajuste en tobillos",
                imagenRes = R.drawable.ropa2,
                stock = 5
            ),
            ProductoEntity(
                id = 3,
                nombre = "Chaqueta Jeans Unisex",
                precio = 24990.0,
                descripcion = "Chaqueta de mezclilla clásica",
                imagenRes = R.drawable.ropa3,
                stock = 3
            )
        )

        // Insertar productos solo si la base está vacía
        lifecycleScope.launch {
            val productosExistentes = database.productoDao().obtenerProductosDirecto()
            if (productosExistentes.isEmpty()) {
                demo.forEach { database.productoDao().insertar(it) }
                println(" Productos insertados correctamente en la base de datos.")
            } else {
                println(" Ya existen productos en la base (${productosExistentes.size})")
            }
        }

        //  Cargar la interfaz
        setContent {
            AromisMovilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AromisApp(
                        productoViewModel,
                        carritoViewModel,
                        pedidoViewModel,
                        usuarioViewModel
                    )
                }
            }
        }
    }
}

@Composable
private fun AromisApp(
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel,
    pedidoViewModel: PedidoViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    val navController: NavHostController = rememberNavController()
    val esAdmin = usuarioViewModel.esAdministrador.collectAsState()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") { LoginScreen(navController, usuarioViewModel) }

        composable("catalogo") {
            CatalogoScreen(
                navController = navController,
                productoViewModel = productoViewModel,
                carritoViewModel = carritoViewModel
            )
        }

        composable("carrito") {
            CarritoScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                pedidoViewModel = pedidoViewModel,
                usuarioViewModel = usuarioViewModel
            )
        }

        composable("confirmacion") { ConfirmacionScreen(navController, usuarioViewModel) }
        composable("perfil") { PerfilScreen(usuarioViewModel, navController) }
        composable("historial") { HistorialPedidosScreen(navController, pedidoViewModel) }

        composable("gestion") {
            if (esAdmin.value)
                GestionProductosScreen(navController, productoViewModel)
            else
                navController.navigate("catalogo")
        }

        composable("detalle/{productoId}") { backStack ->
            val id = backStack.arguments?.getString("productoId")?.toIntOrNull()
            id?.let { productoId ->
                productoViewModel.productos.value.find { it.id == productoId }?.let { producto ->
                    DetalleProductoScreen(
                        producto = producto,
                        navController = navController,
                        productoViewModel = productoViewModel,
                        carritoViewModel = carritoViewModel
                    )
                }
            }
        }
    }
}
