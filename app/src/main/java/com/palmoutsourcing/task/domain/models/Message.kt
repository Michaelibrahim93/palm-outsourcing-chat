package com.palmoutsourcing.task.domain.models

typealias Messages = List<Message>

data class Message(
    val author: String,
    val body: String,
    val isMine: Boolean
)