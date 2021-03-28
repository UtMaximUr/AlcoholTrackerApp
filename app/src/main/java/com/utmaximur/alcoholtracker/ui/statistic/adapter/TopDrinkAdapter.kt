package com.utmaximur.alcoholtracker.ui.statistic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.util.getIdRaw
import com.utmaximur.alcoholtracker.util.getResString

class TopDrinkAdapter(private var drinksDrunkByMe: Map<String, Int>) :
    RecyclerView.Adapter<TopDrinkAdapter.ViewHolder>() {

    private var drinkList: List<Drink>? = null

    fun setDrinkList(drinkList: List<Drink>) {
        this.drinkList = drinkList
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_top_drink, parent, false)) {
        private var drinkText: TextView? = null
        private var drinkCountText: TextView? = null
        private var drinkImage: ImageView? = null
        private var context: Context? = null

        init {
            drinkText = itemView.findViewById(R.id.item_top_drink_text)
            drinkCountText = itemView.findViewById(R.id.item_top_drink_count)
            drinkImage = itemView.findViewById(R.id.item_top_drink_image)
            context = parent.context
        }

        fun bind(drink: Drink, drinksDrunkByMe: Map<String, Int>) {
            drinkText?.text = drink.drink.getResString(itemView.context)
            Glide.with(itemView).load(
                drink.icon.getIdRaw(itemView.context)
            ).into(drinkImage!!)
            drinksDrunkByMe.forEach {
                if (it.key == drink.drink) {
                    drinkCountText?.text = String.format(
                        context!!.resources.getString(R.string.statistic_count_drink),
                        it.value
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drink: Drink = drinkList!![position]
        holder.bind(drink, drinksDrunkByMe)
    }

    override fun getItemCount(): Int {
        return drinkList?.size!!
    }
}