package com.example.santra.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.LoginTable



@Database(entities = [LoginTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun santraDao(): SantraDao
}