package com.utmaximur.alcoholtracker.presantation.statistic.adapter

import androidx.recyclerview.widget.DiffUtil
import com.utmaximur.alcoholtracker.domain.entity.DrinkStatistic


class TopDrinkDiffCallback : DiffUtil.ItemCallback<DrinkStatistic>() {
    override fun areItemsTheSame(oldItem: DrinkStatistic, newItem: DrinkStatistic): Boolean {
        return (oldItem.icon == newItem.icon)
    }

    override fun areContentsTheSame(oldItem: DrinkStatistic, newItem: DrinkStatistic): Boolean {
        return (oldItem == newItem)
    }

}