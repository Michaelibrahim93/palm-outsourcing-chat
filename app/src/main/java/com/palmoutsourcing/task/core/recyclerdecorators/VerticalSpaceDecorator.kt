package com.palmoutsourcing.task.core.recyclerdecorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.palmoutsourcing.task.core.extensions.isLastItem

class VerticalSpaceDecorator(
    private val space: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val isLastItem = state.isLastItem(position)

        outRect.top = space
        outRect.bottom = if (isLastItem) space else 0
    }
}