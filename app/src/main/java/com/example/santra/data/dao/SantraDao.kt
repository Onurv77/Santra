package com.example.santra.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
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

    @Query("""
    SELECT LoginTable.userName 
    FROM PostParticipantsTable 
    INNER JOIN LoginTable ON PostParticipantsTable.studentId = LoginTable.studentId 
    WHERE PostParticipantsTable.postId = :postId
""")
    suspend fun getParticipantUsernamesByPostId(postId: Int): List<String>

    @Query("SELECT COUNT(*) FROM PostParticipantsTable WHERE postId = :postId")
    suspend fun getParticipantCount(postId: Int): Int

    @Query("SELECT studentId FROM PostParticipantsTable WHERE postId = :postId")
    suspend fun getstudentIdFromParticipantbyPostId(postId: Int): List<String>

    @Query("SELECT * FROM PostParticipantsTable WHERE postId = :postId")
    suspend fun getParticipantsByPostId(postId: Int): List<PostParticipantsTable>

    @Query("SELECT COUNT(*) > 0 FROM PostParticipantsTable WHERE postId = :postId AND studentId = :studentId")
    suspend fun isParticipantExists(postId: Int, studentId: String): Boolean

    @Query("DELETE FROM PostParticipantsTable WHERE postId = :postId AND studentId = :studentId")
    suspend fun deleteParticipant(postId: Int, studentId: String)

    //postwithprofile
    @Query(
        """
        SELECT p.id AS postId,
               p.studentId As postStudentId,
               p.description AS postDescription,
               p.participantNum AS postParticipantNum,
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

