package com.example.santra.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.LoginTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val santraDao: SantraDao): ViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    fun login(studentnumber: String, studentpassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = santraDao.login(studentnumber, studentpassword)
            _loginState.postValue(user != null)
        }
    }

}