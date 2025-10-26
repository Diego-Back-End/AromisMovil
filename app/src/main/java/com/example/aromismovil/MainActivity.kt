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

// Clase principal de la aplicación Aromis Móvil.
// Aquí se inicializan los ViewModels, la base de datos, los datos de ejemplo
// y se configura toda la navegación entre pantallas.
class MainActivity : ComponentActivity() {

    // Declaración de los ViewModels que manejarán la lógica de cada módulo.
    private lateinit var productoViewModel: ProductoViewModel
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val carritoViewModel: CarritoViewModel by viewModels()
    private val pedidoViewModel: PedidoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se obtiene la base de datos local y el repositorio de productos.
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ProductoRepository(database.productoDao())

        // Se crea una fábrica para poder pasar el repositorio al ViewModel de productos.
        val factory = ProductoViewModelFactory(repository)
        productoViewModel = ViewModelProvider(this, factory)[ProductoViewModel::class.java]

        // Lista de productos de ejemplo para mostrar al inicio.
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

        // Se insertan los productos de ejemplo solo si la base de datos está vacía.
        lifecycleScope.launch {
            val productos = productoViewModel.productos.value
            if (productos.isEmpty()) demo.forEach { productoViewModel.agregarProducto(it) }
        }

        // Se establece el contenido visual de la app con Jetpack Compose.
        setContent {
            AromisMovilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Se llama a la función principal que contiene la navegación.
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

// Función principal que define la estructura de navegación entre pantallas de la app.
@Composable
private fun AromisApp(
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel,
    pedidoViewModel: PedidoViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    // Controlador de navegación.
    val navController: NavHostController = rememberNavController()

    // Observa si el usuario tiene rol de administrador.
    val esAdmin = usuarioViewModel.esAdministrador.collectAsState()

    // Configuración de las rutas (pantallas) disponibles en la aplicación.
    NavHost(navController = navController, startDestination = "login") {

        // Pantalla de inicio de sesión.
        composable("login") { LoginScreen(navController, usuarioViewModel) }

        // Pantalla del catálogo de productos.
        composable("catalogo") {
            CatalogoScreen(
                navController = navController,
                productoViewModel = productoViewModel,
                carritoViewModel = carritoViewModel
            )
        }

        // Pantalla del carrito de compras.
        composable("carrito") {
            CarritoScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                pedidoViewModel = pedidoViewModel,
                usuarioViewModel = usuarioViewModel
            )
        }

        // Pantalla de confirmación de compra.
        composable("confirmacion") { ConfirmacionScreen(navController, usuarioViewModel) }

        // Pantalla del perfil del usuario.
        composable("perfil") { PerfilScreen(usuarioViewModel, navController) }

        // Pantalla del historial de pedidos.
        composable("historial") { HistorialPedidosScreen(navController, pedidoViewModel) }

        // Pantalla de gestión de productos (solo accesible para administradores).
        composable("gestion") {
            if (esAdmin.value) GestionProductosScreen(navController, productoViewModel)
            else navController.navigate("catalogo") // Redirige si no tiene permisos.
        }

        // Pantalla de detalle del producto.
        composable("detalle/{productoId}") { backStack ->
            val id = backStack.arguments?.getString("productoId")?.toIntOrNull()
            id?.let { productoId ->
                // Busca el producto según su ID y lo muestra en la pantalla de detalle.
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
