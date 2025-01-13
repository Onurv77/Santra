package com.example.santra.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.PostParticipantsTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostParticipantsViewModel(private val santraDao: SantraDao): ViewModel() {

    fun addParticipant(postId: Int, userName: String, studentId: String, maxParticipants: Int, onSuccess: () -> Unit, onFailure: (String) -> Unit) {

        viewModelScope.launch(Dispatchers.IO) {
            val currentCount = santraDao.getParticipantCount(postId)
            if (currentCount < maxParticipants) {
                val participant = PostParticipantsTable(postId = postId, studentId = studentId, userName = userName)
                santraDao.insertPostParticipantsTable(participant)
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } else {
                withContext(Dispatchers.Main) {
                    onFailure("Maksimum katılımcı sayısına ulaşıldı.")
                }
            }
        }

    }

    suspend fun isFull(postId: Int, maxParticipants: Int): Boolean {
            val currentCount = santraDao.getParticipantCount(postId)
            if (maxParticipants > currentCount) {
                return false
            } else {
                return true
            }
    }

    fun removeParticipant(postId: Int, studentId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                santraDao.deleteParticipant(postId, studentId)
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailure(e.message ?: "Bir hata oluştu.")
                }
            }
        }
    }

    suspend fun isUserParticipant(postId: Int, studentId: String): Boolean {
        return santraDao.isParticipantExists(postId, studentId)
    }

    suspend fun getstudentIdFromParticipantbyPostId(postId: Int): List<String> {
        return withContext(Dispatchers.IO) {
            santraDao.getstudentIdFromParticipantbyPostId(postId)
        }
    }

    fun getParticipantUsernames(postId: Int): LiveData<List<String>> {
        val usernamesLiveData = MutableLiveData<List<String>>()
        viewModelScope.launch {
            val usernames = santraDao.getParticipantUsernamesByPostId(postId)
            usernamesLiveData.postValue(usernames)
        }
        return usernamesLiveData
    }

    private val _participantUsernames = MutableLiveData<List<String>>()
    val participantUsernames: LiveData<List<String>> get() = _participantUsernames

    fun refreshParticipants(postId: Int) {
        viewModelScope.launch {
            val updatedList = santraDao.getParticipantUsernamesByPostId(postId)
            _participantUsernames.value = updatedList
        }
    }

}