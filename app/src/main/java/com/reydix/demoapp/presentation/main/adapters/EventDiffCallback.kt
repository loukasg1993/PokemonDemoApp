package com.reydix.demoapp.presentation.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.reydix.demoapp.model.Event

class EventDiffCallback(private val oldList: List<Event>, private val newList: List<Event>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }
    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}