package com.reydix.demoapp.presentation.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reydix.demoapp.model.Stats
import com.reydix.demoapp.databinding.ProgressBarItemBinding

class ProgressBarAdapter(private var itemList: ArrayList<Stats>) : RecyclerView.Adapter<ProgressBarAdapter.ProgressBarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressBarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProgressBarItemBinding.inflate(inflater, parent, false)
        return ProgressBarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgressBarViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateList(statsList: List<Stats>) {
        val diffCallback = ProgressBarDiffCallback(itemList, statsList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        itemList.clear()
        itemList.addAll(statsList)

        diffResult.dispatchUpdatesTo(this)
    }


    inner class ProgressBarViewHolder(private val binding: ProgressBarItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Stats) {
            binding.statNameTxt.text = item.stat.name
            binding.progressNumberTxt.text = "${item.base_stat}/200"
            binding.seekBar.progress = item.base_stat
            binding.seekBar.max = 200
        }
    }
}