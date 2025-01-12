package com.example.santra.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.santra.data.dao.SantraDao

class ChatViewModelFactory(private val santraDao: SantraDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(santraDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}