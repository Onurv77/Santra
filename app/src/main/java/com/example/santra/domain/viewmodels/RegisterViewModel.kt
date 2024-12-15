package com.example.santra.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.LoginTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val santraDao: SantraDao): ViewModel() {

    fun insertUser(studentNumber: String, studentMail: String, studentPassword: String) {
        val newUser = LoginTable(
            StudentNumber = studentNumber,
            StudentMail = studentMail,
            StudentPassword = studentPassword
        )
        viewModelScope.launch(Dispatchers.IO) {
            santraDao.insertAll(newUser)
        }
    }

}