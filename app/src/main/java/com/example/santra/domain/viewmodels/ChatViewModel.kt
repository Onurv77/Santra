package com.example.santra.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.GroupChatsTable
import com.example.santra.data.entities.MessagesTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChatViewModel(private val santraDao: SantraDao) : ViewModel() {

    fun insertGroupChatsTable(groupChatsTable: GroupChatsTable) {
        viewModelScope.launch(Dispatchers.IO) {
            santraDao.insertGroupChatsTable(groupChatsTable)
        }
    }

    fun removeGroupChatsTable(studentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            santraDao.removeGroupChatsTablebyPostId(studentId)
        }
    }

    fun updateLastTimeAndMessageFromGroupTableByPostId(lastMessage: String, lastMessageTime: Long, groupName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            santraDao.updateGroupTableTimeByGroupName(lastMessageTime, groupName)
            santraDao.updateGorupTableLastMessageByGroupName(lastMessage, groupName)
        }
    }

    suspend fun getPostIdFromGroupChatsTableByChatId(chatId: Int): Int {
        return santraDao.getPostIdFromGroupChatsTableByChatId(chatId)
    }

    private val _getOtherGroupChat = MutableLiveData<List<GroupChatsTable>>()
    val getOtherGroupChat: LiveData<List<GroupChatsTable>> = _getOtherGroupChat

    fun getOtherGroupChatsTable(studentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _getOtherGroupChat.postValue(santraDao.getGroupChatsTableOthers(studentId))
        }
    }

    private val _getChatTable = MutableLiveData<List<GroupChatsTable>>()
    val getChatTable: LiveData<List<GroupChatsTable>> = _getChatTable

    fun getChatTablebyPostId(studentId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val get = santraDao.getChatTablebyPostId(studentId)
            _getChatTable.postValue(get)
        }
    }

    suspend fun getPostIdbyStudentId(studentId: String): Int {
        return santraDao.getPostIdbyStudentId(studentId)
    }

    fun insertMessageTable(messagesTable: MessagesTable) {
        viewModelScope.launch(Dispatchers.IO) {
            santraDao.insertMessagesTable(messagesTable)
        }
    }

    private val _getMessagesTableByChatId = MutableLiveData<List<MessagesTable>>()
    val getMessagesTableByChatId: LiveData<List<MessagesTable>> = _getMessagesTableByChatId

    fun getMessagesTableByChatId(groupName: String) {
        viewModelScope.launch(Dispatchers.IO){
            _getMessagesTableByChatId.postValue(santraDao.getMessagesTableByChatId(groupName))
        }
    }

    fun refreshMessagesTable(groupName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val update = santraDao.getMessagesTableByChatId(groupName)
            _getMessagesTableByChatId.postValue(update)
        }
    }

    fun getUserNameFromLoginTable(studentId: String): LiveData<String> {
        val userName = MutableLiveData<String>()
        viewModelScope.launch(Dispatchers.IO) {
            val result = santraDao.getUserNameFromLoginTable(studentId)
            withContext(Dispatchers.Main) {
                userName.value = result
            }
        }
        return userName
    }

    suspend fun getStudentIdFromPostTableByGroupName(groupName: String): String {
        return withContext(Dispatchers.IO) {
            santraDao.getStudentIdFromPostTableByGroupName(groupName = groupName)
        }

    }

    suspend fun getPhotoFromProfileByStudentId(studentId: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            santraDao.getAvatarFromProfileTableByStudentId(studentId)
        }
    }
}