package com.palmoutsourcing.task.domain.repository

import com.palmoutsourcing.task.domain.models.Messages
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {
    fun listenToChatMessages(): Flow<Messages>
}