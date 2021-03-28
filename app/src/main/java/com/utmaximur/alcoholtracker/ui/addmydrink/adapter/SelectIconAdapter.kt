package com.utmaximur.alcoholtracker.ui.addmydrink.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.Icon
import com.utmaximur.alcoholtracker.util.dpToPx


class SelectIconAdapter(private val onClick: (Icon) -> Unit, private var selectedIcon: Icon?) :
    ListAdapter<Icon, SelectIconAdapter.ViewHolder>(IconDiffCallback) {

    interface SelectIconListener {
        fun selectIcon(icon: Icon)
    }

    class ViewHolder(itemView: View, val onClick: (Icon) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.item_icon)
        private var currentIcon: Icon? = null
        private var selectIconListener: SelectIconListener? = null

        init {
            itemView.setOnClickListener {
                currentIcon?.let {
                    onClick(it)
                    selectIconListener?.selectIcon(it)
                }
            }
        }

        fun bind(icon: Icon, selectIconListener: SelectIconListener) {
            setListener(selectIconListener)
            currentIcon = icon
            Glide.with(itemView).load(icon.icon).override(45.dpToPx(), 45.dpToPx()).into(
                iconImageView
            )
        }

        fun setListener(selectIconListener: SelectIconListener) {
            this.selectIconListener = selectIconListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_select_icon, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val icon = getItem(position)
        holder.itemView.alpha = if (selectedIcon == null || icon == selectedIcon) 1f else 0.2f
        holder.bind(icon, object : SelectIconListener {
            override fun selectIcon(icon: Icon) {
                selectedIcon = icon
                notifyDataSetChanged()
            }
        })

    }
}

object IconDiffCallback : DiffUtil.ItemCallback<Icon>() {
    override fun areItemsTheSame(oldItem: Icon, newItem: Icon): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Icon, newItem: Icon): Boolean {
        return oldItem.icon == newItem.icon
    }
}
