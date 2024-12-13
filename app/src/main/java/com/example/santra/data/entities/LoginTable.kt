package com.example.santra.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginTable(
    @ColumnInfo(name = "StudentNumber") val StudentNumber: String,
    @ColumnInfo(name = "StudentMail") val StudentMail: String,
    @ColumnInfo(name = "StudentPassword") val StudentPassword: String

) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}

