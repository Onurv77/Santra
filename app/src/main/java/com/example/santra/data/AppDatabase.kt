package com.example.santra.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.GroupChatsTable
import com.example.santra.data.entities.LoginTable
import com.example.santra.data.entities.MessagesTable
import com.example.santra.data.entities.PostParticipantsTable
import com.example.santra.data.entities.PostTable
import com.example.santra.data.entities.PrivateChatsTable
import com.example.santra.data.entities.ProfileTable


@Database(entities = [LoginTable::class,
    ProfileTable::class,
    PostTable::class,
    PostParticipantsTable::class,
    MessagesTable::class,
    GroupChatsTable::class,
    PrivateChatsTable::class], version = 1)
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