package com.palmoutsourcing.task.presentaion.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmoutsourcing.task.data.MessagesRepositoryImpl
import com.palmoutsourcing.task.domain.models.Message
import com.palmoutsourcing.task.domain.repository.MessagesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(
    messagesRepository: MessagesRepository = MessagesRepositoryImpl(),
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    init {
        viewModelScope.launch {
            messagesRepository
                .listenToChatMessages()
                .collect { newMessages -> _messages.value = newMessages }
        }
    }
}