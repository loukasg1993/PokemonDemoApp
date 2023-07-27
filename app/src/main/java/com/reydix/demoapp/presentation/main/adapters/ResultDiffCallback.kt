package com.reydix.demoapp.presentation.main.adapters
import androidx.recyclerview.widget.DiffUtil
import com.reydix.demoapp.model.Pokemon

class ResultDiffCallback(private val oldList: List<Pokemon>, private val newList: List<Pokemon>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].id == newList[newPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition] == newList[newPosition]
    }
}