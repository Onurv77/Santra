package com.example.santra.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.LoginTable
import com.example.santra.data.entities.ProfileTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val santraDao: SantraDao): ViewModel() {

    fun registerUser(studentId: String,
                     mail: String,
                     studentPassword: String,
                     phone: String,
                     userName: String,
                     rank: String? = null,
                     honor: String? = null,
                     avatar: ByteArray? = null) {
        val loginTable = LoginTable(
            studentId = studentId,
            studentPassword = studentPassword,
            userName = userName
        )

        val profileTable = ProfileTable(
            studentId = studentId,
            userName = userName,
            avatar = avatar,
            rank = rank,
            honor = honor,
            mail = mail,
            phone = phone
        )

        viewModelScope.launch(Dispatchers.IO) {
            santraDao.insertLoginTable(loginTable)
            santraDao.insertProfileTable(profileTable)
        }
    }

}