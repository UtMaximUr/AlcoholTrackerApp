package com.utmaximur.alcoholtracker.presentation.addmydrink.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.domain.entity.Icon
import com.utmaximur.alcoholtracker.util.setImageOverrideSize


class SelectIconAdapter(private val onClick: (Icon) -> Unit, private var selectedIconDBO: Icon?) :
    ListAdapter<Icon, SelectIconAdapter.ViewHolder>(IconDiffCallback) {


    class ViewHolder(itemView: View, val onClick: (Icon) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.item_icon)
        private var currentIconDBO: Icon? = null
        private var onSelectedIcon: ((Icon) -> Unit?)? = null

        init {
            itemView.setOnClickListener {
                currentIconDBO?.let { icon ->
                    onClick(icon)
                    onSelectedIcon?.let { onClick -> onClick(icon) }
                }
            }
        }

        fun bind(iconDBO: Icon, onSelectedIcon: (Icon) -> Unit) {
            currentIconDBO = iconDBO
            this.onSelectedIcon = onSelectedIcon
            iconImageView.setImageOverrideSize(iconDBO.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_select_icon, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val icon = getItem(position)
        holder.itemView.alpha = if (selectedIconDBO == null || icon == selectedIconDBO) 1f else 0.2f
        holder.bind(icon) { selectIcon ->
            selectedIconDBO = selectIcon
            notifyDataSetChanged()
        }
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
