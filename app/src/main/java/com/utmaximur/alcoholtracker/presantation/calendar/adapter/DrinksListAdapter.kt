package com.utmaximur.alcoholtracker.presantation.calendar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ToggleButton
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.databinding.ItemDrinkBinding
import com.utmaximur.alcoholtracker.domain.entity.TrackCalendar
import com.utmaximur.alcoholtracker.util.*


class DrinksListAdapter(
    private val listener: OnDrinkAdapterListener
) : ListAdapter<TrackCalendar, DrinksListAdapter.ViewHolder>(DrinksDiffCallback()) {

    interface OnDrinkAdapterListener {
        fun onEdit(date: Long)
        fun onDelete(track: TrackCalendar, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDrinkBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemDrinkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.editButton.setOnClickListener {
                listener.onEdit(getItem(layoutPosition).date)
            }
            binding.deleteButton.setOnClickListener {
                listener.onDelete(getItem(layoutPosition), layoutPosition)
            }
            binding.eventButton.setOnCheckedChangeListener { _, b ->
                showLayout(
                    b,
                    binding.eventLinearLayout,
                    binding.infoLinearLayout,
                    binding.infoButton
                )
            }

            binding.infoButton.setOnCheckedChangeListener { _, b ->
                showLayout(
                    b,
                    binding.infoLinearLayout,
                    binding.eventLinearLayout,
                    binding.eventButton
                )
            }
        }

        fun bind(
            track: TrackCalendar
        ) {
            binding.itemVolumeText.text =
                track.volume.formatVolume(itemView.context, track.quantity)
            binding.itemDrinkText.text = String.format(
                itemView.context.resources.getString(R.string.calendar_count_drink),
                track.drink.getResString(itemView.context), track.quantity
            )
            binding.itemDegreeText.text = track.degree
            binding.itemPriceText.text = (track.price * track.quantity).toString()

            if (track.event.isEmpty()) {
                binding.eventButton.toGone()
            }

            binding.eventText.text = track.event
            binding.infoText.text = track.getSafeDoseOfAlcohol(itemView.context)
        }

        private fun showLayout(
            b: Boolean,
            l1: LinearLayout?,
            l2: LinearLayout?,
            button: ToggleButton?
        ) {
            if (b) {
                if (binding.drinkLinearLayout.visibility == View.VISIBLE) {
                    binding.drinkLinearLayout.alphaViewOut()
                }
                l1?.alphaViewIn()
                if (button?.isChecked!!) {
                    button.isChecked = false
                }
            } else {
                l1?.alphaViewOut()
                if (l2?.visibility != View.VISIBLE) {
                    binding.drinkLinearLayout.alphaViewIn()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}