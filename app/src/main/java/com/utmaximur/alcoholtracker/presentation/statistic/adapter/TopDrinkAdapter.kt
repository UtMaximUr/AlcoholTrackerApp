package com.utmaximur.alcoholtracker.presentation.statistic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.databinding.ItemTopDrinkBinding
import com.utmaximur.alcoholtracker.domain.entity.DrinkStatistic
import com.utmaximur.alcoholtracker.util.getResString

class TopDrinkAdapter :
    ListAdapter<DrinkStatistic, TopDrinkAdapter.TopDrinkViewHolder>(TopDrinkDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopDrinkViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTopDrinkBinding.inflate(layoutInflater, parent, false)
        return TopDrinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopDrinkViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class TopDrinkViewHolder(
        private val binding: ItemTopDrinkBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(drink: DrinkStatistic) {
            binding.itemTopDrinkText.text = drink.drink.getResString(itemView.context)
            binding.itemTopDrinkImage.setImage(drink.icon)
            binding.itemTopDrinkCount.text = String.format(
                binding.root.context!!.resources.getString(R.string.statistic_count_drink),
                drink.count
            )
        }
    }
}