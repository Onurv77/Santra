package com.example.santra.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.santra.data.entities.LoginTable
import com.example.santra.data.entities.PostParticipantsTable
import com.example.santra.data.entities.PostTable
import com.example.santra.data.entities.PostWithProfile
import com.example.santra.data.entities.ProfileTable

@Dao
interface SantraDao {

    //LoginTable
    @Query("SELECT * FROM LoginTable")
    fun getAll(): List<LoginTable>

    @Insert
    suspend fun insertLoginTable(logintableinsert: LoginTable)

    @Query("SELECT * FROM LoginTable WHERE studentId = :studentNumber AND StudentPassword = :studentPassword")
    fun login(studentNumber: String, studentPassword: String): LoginTable?


    //ProfileTable
    @Insert
    suspend fun insertProfileTable(profile: ProfileTable)

    @Query("SELECT * FROM ProfileTable WHERE studentId = :studentId")
    suspend fun getProfileByStudentId(studentId: String): ProfileTable?


    //PostTable
    @Insert
    suspend fun insertPostsbyStudentId(post: PostTable)

    @Query("SELECT * FROM PostTable WHERE studentId = :studentId")
    suspend fun getPostTable(studentId: String): List<PostTable>

    @Query("DELETE FROM PostTable WHERE date < :currentTime")
    suspend fun deletePostsOlderThan(currentTime: Long)


    //PostParticipantsTable
    @Insert
    suspend fun insertPostParticipantsTable(postParticipantsTable: PostParticipantsTable)

    @Query("SELECT COUNT(*) FROM PostParticipantsTable WHERE postId = :postId")
    suspend fun getParticipantCount(postId: Int): Int

    @Query("SELECT * FROM PostParticipantsTable WHERE postId = :postId")
    suspend fun getParticipantsByPostId(postId: Int): List<PostParticipantsTable>

    @Query(
        """
        SELECT p.id AS postId, 
               p.description AS postDescription, 
               p.date AS postDate, 
               p.mevki AS postMevki, 
               pr.username AS profileUsername, 
               pr.avatar AS profileAvatarUrl,                
               pr.phone AS profilePhone
        FROM PostTable p
        INNER JOIN ProfileTable pr ON p.studentId = pr.studentId
        """
    )
    fun getPostsWithProfile(): LiveData<List<PostWithProfile>>

    @Query("DELETE FROM LoginTable")
    suspend fun deleteAll()

}

