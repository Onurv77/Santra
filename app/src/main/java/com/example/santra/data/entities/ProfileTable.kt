package com.example.santra.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["studentId"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = LoginTable::class,
        parentColumns = arrayOf("studentId"),
        childColumns = arrayOf("studentId"),
        onDelete = ForeignKey.CASCADE
    )])
data class ProfileTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "studentId") val studentId: String,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "avatar", typeAffinity = ColumnInfo.BLOB) val avatar: ByteArray?,
    @ColumnInfo(name = "rank") val rank: String?,
    @ColumnInfo(name = "honor") val honor: String?,
    @ColumnInfo(name = "mail") val mail: String,
    @ColumnInfo(name = "phone") val phone: String?
)