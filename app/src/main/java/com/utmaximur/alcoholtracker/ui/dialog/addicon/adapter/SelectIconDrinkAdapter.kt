package com.utmaximur.alcoholtracker.ui.dialog.addicon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.Icon

open class SelectIconDrinkAdapter :
    RecyclerView.Adapter<SelectIconDrinkAdapter.ViewHolder>() {

    interface SelectIconListener {
        fun selectIcon(icon: Int)
    }

    private var icons: List<Icon>? = null
    private var selectIconListener: SelectIconListener? = null

    fun setListener(selectIconListener: SelectIconListener) {
        this.selectIconListener = selectIconListener
    }

    fun setIcons(icons: List<Icon>) {
        this.icons = icons
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_top_drink, parent, false)) {
        private var drinkText: TextView? = null
        private var drinkCountText: TextView? = null
        private var drinkImage: ImageView? = null
        private var context: Context? = null

        init {
            drinkText = itemView.findViewById(R.id.item_top_drink_text)
            drinkText?.visibility = View.GONE
            drinkCountText = itemView.findViewById(R.id.item_top_drink_count)
            drinkCountText?.visibility = View.GONE
            drinkImage = itemView.findViewById(R.id.item_top_drink_image)
            context = parent.context
        }

        fun bind(icon: Icon, selectIconListener: SelectIconListener?) {
            Glide.with(itemView).load(icon.icon).into(drinkImage!!)
            drinkImage?.setOnClickListener {
                selectIconListener?.selectIcon(icon.icon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val icon: Icon = icons!![position]
        holder.bind(icon, selectIconListener)
    }

    override fun getItemCount(): Int {
        return icons?.size!!
    }
}