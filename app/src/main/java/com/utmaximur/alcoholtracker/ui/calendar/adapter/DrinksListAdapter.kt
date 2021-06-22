package com.utmaximur.alcoholtracker.ui.calendar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.databinding.ItemDrinkBinding
import com.utmaximur.alcoholtracker.util.*


class DrinksListAdapter(
    private val alcoholTracks: MutableList<AlcoholTrack>,
    private val listener: OnDrinkAdapterListener
) :
    RecyclerView.Adapter<DrinksListAdapter.ViewHolder>() {

    interface OnDrinkAdapterListener {
        fun onEdit(date: Long)
        fun onDelete(alcoholTrack: AlcoholTrack)
    }

    class ViewHolder(private val binding: ItemDrinkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            alcoholTrack: AlcoholTrack,
            listener: OnDrinkAdapterListener
        ) {
            binding.itemVolumeText.text =
                alcoholTrack.volume.formatVolume(itemView.context, alcoholTrack.quantity)
            binding.itemDrinkText.text = String.format(
                itemView.context.resources.getString(R.string.calendar_count_drink),
                alcoholTrack.drink.getResString(itemView.context), alcoholTrack.quantity
            )
            binding.itemDrinkText.text = alcoholTrack.degree
            binding.itemPriceText.text = (alcoholTrack.price * alcoholTrack.quantity).toString()
            binding.editButton.setOnClickListener {
                listener.onEdit(alcoholTrack.date)
            }
            binding.deleteButton.setOnClickListener {
                listener.onDelete(alcoholTrack)
            }

            if (alcoholTrack.event.isEmpty()) {
                binding.eventButton.toGone()
            }

            binding.eventText.text = alcoholTrack.event
            binding.infoText.text = alcoholTrack.getSafeDoseOfAlcohol(itemView.context)

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
                    binding.eventLinearLayout,
                    binding.infoLinearLayout,
                    binding.infoButton
                )
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDrinkBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return alcoholTracks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alcoholTrack: AlcoholTrack = alcoholTracks[position]
        holder.bind(alcoholTrack, listener)
    }
}