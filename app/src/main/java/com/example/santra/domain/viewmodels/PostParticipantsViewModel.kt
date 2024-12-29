package com.example.santra.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.PostParticipantsTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostParticipantsViewModel(private val santraDao: SantraDao): ViewModel() {

    fun addParticipant(postId: Int, studentId: String, maxParticipants: Int, onSuccess: () -> Unit, onFailure: (String) -> Unit) {

        viewModelScope.launch(Dispatchers.IO) {
            val currentCount = santraDao.getParticipantCount(postId)
            if (currentCount < maxParticipants) {
                val participant = PostParticipantsTable(postId = postId, studentId = studentId)
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

}