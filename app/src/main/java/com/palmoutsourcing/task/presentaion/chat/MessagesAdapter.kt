package com.palmoutsourcing.task.presentaion.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.palmoutsourcing.task.R
import com.palmoutsourcing.task.databinding.ItemMessageBinding
import com.palmoutsourcing.task.domain.models.Message

class MessagesAdapter(
    private val messages: List<Message>
) : RecyclerView.Adapter<MessageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int
    ) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemMessageBinding.bind(itemView)
    fun bind(message: Message) {
        binding.tvAuthor.text = message.author
        binding.tvMessage.text = message.body
    }

}