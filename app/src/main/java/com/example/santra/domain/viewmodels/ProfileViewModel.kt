package com.example.santra.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.ProfileTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val santraDao: SantraDao): ViewModel() {

    private val _profile = MutableLiveData<ProfileTable?>()
    val profile: LiveData<ProfileTable?> = _profile

    fun fetchProfile(studentId: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val profile = santraDao.getProfileByStudentId(studentId)
            _profile.postValue(profile)
        }

    }

    fun updatePhoneFromProfileTableByStudentId(phone: String, studentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            santraDao.updatePhoneFromProfileTableByStudentId(phone, studentId)
        }
    }

    private fun getPasswordFromLogin(studentId: String): String {
        return santraDao.getPasswordFromLogin(studentId)
    }

    fun updatePasswordFromLoginTableByStudentId(oldPassword: String, newPassword: String, studentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if(getPasswordFromLogin(studentId) == oldPassword) {
                santraDao.updatePasswordFromLogin(newPassword, studentId)
            }
        }
    }

    fun updateAboutMe(aboutMe:String, studentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            santraDao.updateAboutMe(aboutMe, studentId)
        }
    }

}