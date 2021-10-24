package com.utmaximur.alcoholtracker.presentation.create_my_drink.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.R


class SelectVolumeAdapter(private val onClick: (String?) -> Unit, private val volumes: List<String?>?) :
    ListAdapter<String?, SelectVolumeAdapter.ViewHolder>(VolumeDiffCallback) {

    class ViewHolder(itemView: View, val onClick: (String?) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val volumeCheckedTextView: CheckedTextView =
            itemView.findViewById(R.id.item_checked_volume)
        private var currentVolume: String? = null

        init {
            itemView.setOnClickListener {
                currentVolume?.let {
                    onClick(it)
                }
                volumeCheckedTextView.isChecked = !volumeCheckedTextView.isChecked
            }
        }

        fun bind(volume: String?, volumes: List<String?>?) {
            currentVolume = volume
            volumeCheckedTextView.text = String.format(itemView.context.getString(R.string.unit_l), volume)
            if(volumes != null && volumes.contains(volume)){
                volumeCheckedTextView.isChecked = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_select_volume, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val volume = getItem(position)
        holder.bind(volume, volumes)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

object VolumeDiffCallback : DiffUtil.ItemCallback<String?>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
