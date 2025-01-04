package com.example.santra.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.santra.data.dao.SantraDao

class PostParticipantsViewModelFactory(private val santraDao: SantraDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostParticipantsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostParticipantsViewModel(santraDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}