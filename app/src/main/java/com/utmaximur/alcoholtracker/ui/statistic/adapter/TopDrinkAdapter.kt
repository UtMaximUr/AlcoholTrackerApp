package com.utmaximur.alcoholtracker.ui.statistic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.databinding.ItemTopDrinkBinding
import com.utmaximur.alcoholtracker.util.getResString
import com.utmaximur.alcoholtracker.util.setImage

class TopDrinkAdapter(
    private var drinkList: List<Drink>,
    private var drinksDrunkByMe: Map<String, Int>
) :
    RecyclerView.Adapter<TopDrinkAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemTopDrinkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink, drinksDrunkByMe: Map<String, Int>) {
            binding.itemTopDrinkText.text = drink.drink.getResString(itemView.context)
            binding.itemTopDrinkImage.setImage(drink.icon)
            drinksDrunkByMe.forEach {
                if (it.key == drink.drink) {
                    binding.itemTopDrinkCount.text = String.format(
                        binding.root.context!!.resources.getString(R.string.statistic_count_drink),
                        it.value
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTopDrinkBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drink: Drink = drinkList[position]
        holder.bind(drink, drinksDrunkByMe)
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }
}