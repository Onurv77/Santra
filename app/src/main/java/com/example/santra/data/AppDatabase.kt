package com.example.santra.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.LoginTable



@Database(entities = [LoginTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun santraDao(): SantraDao

    companion object {
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Santra_Database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}