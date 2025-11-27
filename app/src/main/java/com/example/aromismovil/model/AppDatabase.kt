package com.example.aromismovil.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Esta clase representa la base de datos local de la aplicación.
// Usa Room, que internamente utiliza SQLite para almacenar la información en el dispositivo.
@Database(
    entities = [
        //tablas
        ProductoEntity::class,
        CarritoEntity::class,
        PedidoEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    //dao con las consultas
    abstract fun productoDao(): ProductoDao
    abstract fun carritoDao(): CarritoDao
    abstract fun pedidoDao(): PedidoDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "aromis_db"
                )
                    .fallbackToDestructiveMigration() // recrea la base
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
