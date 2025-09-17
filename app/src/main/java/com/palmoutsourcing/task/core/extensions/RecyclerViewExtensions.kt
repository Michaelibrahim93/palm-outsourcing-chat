package com.palmoutsourcing.task.core.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.State.isLastItem(position: Int): Boolean {
    return position == itemCount - 1
}