package com.reydix.demoapp.presentation.main.adapters
import androidx.recyclerview.widget.DiffUtil
import com.reydix.demoapp.model.Stats

class ProgressBarDiffCallback(private val oldList: List<Stats>, private val newList: List<Stats>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].stat.name == newList[newItemPosition].stat.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}