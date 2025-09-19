package com.palmoutsourcing.task.presentaion.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.palmoutsourcing.task.core.initloader.InitDataLoader
import com.palmoutsourcing.task.core.initloader.InitDataLoaderImpl
import com.palmoutsourcing.task.data.MessagesRepositoryImpl
import com.palmoutsourcing.task.domain.models.Message
import com.palmoutsourcing.task.domain.repository.MessagesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(
    private val messagesRepository: MessagesRepository = MessagesRepositoryImpl()
) : ViewModel(), InitDataLoader by InitDataLoaderImpl() {
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    fun loadMessages() = loadInitData {
        viewModelScope.launch {
            messagesRepository
                .listenToChatMessages()
                .collect { newMessages -> _messages.value = newMessages }
        }
    }
}

class ChatViewModelFactory(
    private val messagesRepository: MessagesRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(messagesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}