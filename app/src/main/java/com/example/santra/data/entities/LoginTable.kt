package com.example.santra.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["studentId"], unique = true)])
data class LoginTable(
    @PrimaryKey(autoGenerate = true ) val id: Int = 0,
    @ColumnInfo(name = "studentId") val studentId: String,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "studentPassword") val studentPassword: String

)