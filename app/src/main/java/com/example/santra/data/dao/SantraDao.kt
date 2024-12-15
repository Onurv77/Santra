package com.example.santra.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.santra.data.entities.LoginTable

@Dao
interface SantraDao {
    @Query("SELECT * FROM LoginTable")
    fun getAll(): List<LoginTable>

    @Insert
    suspend fun insertAll(logintableinsert: LoginTable)

}
