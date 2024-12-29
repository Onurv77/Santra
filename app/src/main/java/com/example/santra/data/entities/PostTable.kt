package com.example.santra.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "studentId") val studentId: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "mevki") val mevki: String
)