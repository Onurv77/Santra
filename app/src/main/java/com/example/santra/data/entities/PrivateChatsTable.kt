package com.example.santra.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = LoginTable::class,
            parentColumns = arrayOf("studentId"),
            childColumns = arrayOf("participant1Id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LoginTable::class,
            parentColumns = arrayOf("studentId"),
            childColumns = arrayOf("participant2Id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PrivateChatsTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "participant1Id") val participant1Id: String,
    @ColumnInfo(name = "participant2Id") val participant2Id: String,
    @ColumnInfo(name = "lastMessage") val lastMessage: String?,
    @ColumnInfo(name = "lastMessageTime") val lastMessageTime: Long?
)
