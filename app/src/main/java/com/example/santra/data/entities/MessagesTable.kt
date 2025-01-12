package com.example.santra.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = GroupChatsTable::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("chatId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LoginTable::class,
            parentColumns = arrayOf("studentId"),
            childColumns = arrayOf("senderId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MessagesTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "chatId") val chatId: Int,
    @ColumnInfo(name = "groupName") val groupName: String,
    @ColumnInfo(name = "senderId") val senderStudentId: String,
    @ColumnInfo(name = "messageContent") val messageContent: String?,
    @ColumnInfo(name = "timestamp") val timestamp: Long?
)
