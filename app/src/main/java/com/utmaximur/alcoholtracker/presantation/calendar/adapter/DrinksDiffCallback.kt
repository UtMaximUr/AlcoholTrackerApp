package com.utmaximur.alcoholtracker.presantation.calendar.adapter

import androidx.recyclerview.widget.DiffUtil
import com.utmaximur.alcoholtracker.domain.entity.Track


class DrinksDiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return (oldItem == newItem)
    }

}