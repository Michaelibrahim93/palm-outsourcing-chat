package com.palmoutsourcing.task.data

import com.palmoutsourcing.task.domain.models.Message
import com.palmoutsourcing.task.domain.models.Messages
import com.palmoutsourcing.task.domain.repository.MessagesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class MessagesRepositoryImpl : MessagesRepository {
    override fun listenToChatMessages(): Flow<Messages> {
        return flow {
            var latestMessages = emptyList<Message>()
            repeat(10) { batch ->
                val newMessages = generateMessages(batch)
                latestMessages = latestMessages + newMessages
                emit(latestMessages)
                delay(2000)
            }
        }
    }

    private fun generateMessages(batch: Int): List<Message> {
        return (0..2).map {
            val isMine = Random.nextBoolean()
            Message(
                author = if (isMine) "You" else "Other",
                body = "Message Batch: $batch - Count: $it",
                isMine = isMine
            )
        }
    }
}