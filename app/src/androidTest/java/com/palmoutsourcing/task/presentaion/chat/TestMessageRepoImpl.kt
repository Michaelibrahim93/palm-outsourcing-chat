package com.palmoutsourcing.task.presentaion.chat

import com.palmoutsourcing.task.domain.models.Messages
import com.palmoutsourcing.task.domain.repository.MessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestMessageRepoImpl(
    private val messages: Messages? = null
) : MessagesRepository{
    override fun listenToChatMessages(): Flow<Messages> {
        return flow {
            if (messages != null) {
                emit(messages)
            }
        }
    }
}