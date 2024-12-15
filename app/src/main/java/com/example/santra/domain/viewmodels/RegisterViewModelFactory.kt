package com.example.santra.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.santra.data.dao.SantraDao

class RegisterViewModelFactory(private val santraDao: SantraDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(santraDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}