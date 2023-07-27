package com.reydix.demoapp.presentation.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reydix.demoapp.databinding.EventListItemBinding
import com.reydix.demoapp.model.Event

class EventAdapter(private var events: ArrayList<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventListItemBinding.inflate(inflater, parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val result = events[position]
        holder.bind(result)
    }

    override fun getItemCount() = events.size

    fun updateEventList(eventList: List<Event>) {
        events.clear()
        events.addAll(eventList)
        notifyDataSetChanged()

    }

    inner class EventViewHolder(private val binding: EventListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.eventDescription.text = event.description
            binding.eventDate.text = event.date
            binding.eventLocation.text = event.area

            val resourceId = binding.root.context.resources.getIdentifier(
                event.eventImage,
                "drawable",
                binding.root.context.packageName
            )
            binding.eventImage.setImageResource(resourceId)
        }


    }
}
