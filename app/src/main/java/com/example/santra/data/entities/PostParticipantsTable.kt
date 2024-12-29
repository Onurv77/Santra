package com.example.santra.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = PostTable::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("postId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LoginTable::class,
            parentColumns = arrayOf("studentId"),
            childColumns = arrayOf("studentId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PostParticipantsTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "postId") val postId: Int,
    @ColumnInfo(name = "studentId") val studentId: String

)