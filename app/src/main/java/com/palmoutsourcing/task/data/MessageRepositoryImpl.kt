package com.palmoutsourcing.task.data

import com.palmoutsourcing.task.domain.models.Message
import com.palmoutsourcing.task.domain.models.Messages
import com.palmoutsourcing.task.domain.repository.MessagesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class MessageRepositoryImpl : MessagesRepository {
    override fun listenToNewMessages(): Flow<Messages> {
        return flow {
            repeat(10) { batch ->
                emit((1..3).map {
                    val isMine = Random.nextBoolean()
                    Message(
                        author = if (isMine) "Me" else "Other",
                        body = "Message $batch - $it",
                        isMine = isMine
                    )
                })
                delay(3000)
            }
        }
    }
}