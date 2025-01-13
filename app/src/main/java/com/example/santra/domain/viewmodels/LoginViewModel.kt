package com.example.santra.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.santra.data.dao.SantraDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val santraDao: SantraDao): ViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    private val _loggedInStudentId = MutableLiveData<String?>()
    val loggedInStudentId: LiveData<String?> = _loggedInStudentId

    private val _loggedInUserName = MutableLiveData<String?>()
    val loggedInUserName: LiveData<String?> = _loggedInUserName

    fun login(studentId: String, studentPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = santraDao.login(studentId, studentPassword)
            _loginState.postValue(user != null)
            _loggedInStudentId.postValue(user?.studentId)
            _loggedInUserName.postValue(user?.userName)
        }
    }

}