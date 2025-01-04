package com.example.santra.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.PostTable
import com.example.santra.data.entities.PostWithProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PostViewModel(private val santraDao: SantraDao): ViewModel() {

    val postsWithProfile: LiveData<List<PostWithProfile>> = santraDao.getPostsWithProfile()

    fun createPost(studentId: String, description: String, date: Long, mevki: String, participantNum: Int) {

        val post = PostTable(
            studentId = studentId,
            description = description,
            date = date,
            mevki = mevki,
            participantNum = participantNum
        )

        viewModelScope.launch(Dispatchers.IO) {

            santraDao.insertPostsbyStudentId(post)

        }
    }
    fun getPostWithProfileById(postId: Int): LiveData<PostWithProfile?> {
        return santraDao.getPostsWithProfile().map { posts ->
            posts.find { it.postId == postId }
        }
    }

    fun deleteExpiredPosts() {
        viewModelScope.launch {
            val currentTime = System.currentTimeMillis()
            santraDao.deletePostsOlderThan(currentTime)
        }
    }

    fun startExpirationCheck() {
        viewModelScope.launch {
            while (true) {
                deleteExpiredPosts()
                delay(10_000)
            }
        }
    }

    val postWithProfile: LiveData<List<PostWithProfile>> =
        santraDao.getPostsWithProfile().map { posts ->
            posts.sortedBy { it.postDate }
        }

}