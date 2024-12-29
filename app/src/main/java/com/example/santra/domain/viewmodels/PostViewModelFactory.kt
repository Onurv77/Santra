package com.example.santra.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.santra.data.dao.SantraDao

class PostViewModelFactory(private val santraDao: SantraDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(santraDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}