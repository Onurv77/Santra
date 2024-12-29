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

}