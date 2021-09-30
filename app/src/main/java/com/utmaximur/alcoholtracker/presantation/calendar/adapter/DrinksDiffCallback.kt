package com.utmaximur.alcoholtracker.presantation.calendar.adapter

import androidx.recyclerview.widget.DiffUtil
import com.utmaximur.alcoholtracker.data.dbo.TrackDBO
import com.utmaximur.alcoholtracker.domain.entity.TrackCalendar


class DrinksDiffCallback : DiffUtil.ItemCallback<TrackCalendar>() {
    override fun areItemsTheSame(oldItem: TrackCalendar, newItem: TrackCalendar): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: TrackCalendar, newItem: TrackCalendar): Boolean {
        return (oldItem == newItem)
    }

}