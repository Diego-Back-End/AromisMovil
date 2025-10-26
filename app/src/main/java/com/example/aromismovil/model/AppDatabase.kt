package com.example.aromismovil.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Anotación que indica que esta clase define una base de datos de Room.
// Aquí se declara qué entidades (tablas) usará y la versión actual de la base de datos.
@Database(entities = [ProductoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Se declara una función abstracta que permitirá acceder al DAO de productos.
    // El DAO es quien maneja las operaciones de base de datos (insertar, eliminar, etc.).
    abstract fun productoDao(): ProductoDao

    // Objeto que asegura que la base de datos tenga una sola instancia en toda la aplicación.
    companion object {
        // Variable que guarda la instancia única de la base de datos.
        @Volatile private var INSTANCE: AppDatabase? = null

        // Función que crea o devuelve la base de datos si ya existe.
        // Se usa el patrón Singleton para evitar crear varias bases de datos al mismo tiempo.
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Si la base de datos aún no existe, se construye aquí con Room.
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "aromis_db" // Nombre del archivo de base de datos.
                ).build().also { INSTANCE = it } // Se guarda la instancia creada.
            }
        }
    }
}
