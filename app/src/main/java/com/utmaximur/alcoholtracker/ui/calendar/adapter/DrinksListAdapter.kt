package com.utmaximur.alcoholtracker.ui.calendar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
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

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_drink, parent, false)) {
        private var drinkText: TextView? = null
        private var volumeText: TextView? = null
        private var degreeText: TextView? = null
        private var priceText: TextView? = null
        private var eventText: TextView? = null
        private var infoText: TextView? = null
        private var infoButton: ToggleButton? = null
        private var eventButton: ToggleButton? = null
        private var editButton: ImageButton? = null
        private var deleteButton: ImageButton? = null
        private var drinkLayout: LinearLayout? = null
        private var eventLayout: LinearLayout? = null
        private var infoLayout: LinearLayout? = null

        init {
            drinkText = itemView.findViewById(R.id.item_drink_text)
            volumeText = itemView.findViewById(R.id.item_volume_text)
            degreeText = itemView.findViewById(R.id.item_degree_text)
            priceText = itemView.findViewById(R.id.item_price_text)
            infoButton = itemView.findViewById(R.id.info_button)
            eventButton = itemView.findViewById(R.id.event_button)
            editButton = itemView.findViewById(R.id.edit_button)
            eventText = itemView.findViewById(R.id.event_text)
            infoText = itemView.findViewById(R.id.info_text)
            deleteButton = itemView.findViewById(R.id.delete_button)
            drinkLayout = itemView.findViewById(R.id.drink_linearLayout)
            eventLayout = itemView.findViewById(R.id.event_linearLayout)
            infoLayout = itemView.findViewById(R.id.info_linearLayout)
        }

        fun bind(
            alcoholTrack: AlcoholTrack,
            listener: OnDrinkAdapterListener
        ) {
            volumeText?.text =
                alcoholTrack.volume.formatVolume(itemView.context, alcoholTrack.quantity)
            drinkText?.text = String.format(
                itemView.context.resources.getString(R.string.calendar_count_drink),
                alcoholTrack.drink.getResString(itemView.context), alcoholTrack.quantity
            )
            degreeText?.text = alcoholTrack.degree
            priceText?.text = (alcoholTrack.price * alcoholTrack.quantity).toString()
            editButton?.setOnClickListener {
                listener.onEdit(alcoholTrack.date)
            }
            deleteButton?.setOnClickListener {
                listener.onDelete(alcoholTrack)
            }

            if (alcoholTrack.event.isEmpty()) {
                eventButton?.toGone()
            }

            eventText?.text = alcoholTrack.event
            infoText?.text = alcoholTrack.getSafeDoseOfAlcohol(itemView.context)

            eventButton?.setOnCheckedChangeListener { _, b ->
                showLayout(b, eventLayout, infoLayout, infoButton)
            }

            infoButton?.setOnCheckedChangeListener { _, b ->
                showLayout(b, infoLayout, eventLayout, eventButton)
            }
        }

        private fun showLayout(
            b: Boolean,
            l1: LinearLayout?,
            l2: LinearLayout?,
            button: ToggleButton?
        ) {
            if (b) {
                if (drinkLayout?.visibility == View.VISIBLE) {
                    drinkLayout?.alphaViewOut()
                }
                l1?.alphaViewIn()
                if (button?.isChecked!!) {
                    button.isChecked = false
                }
            } else {
                l1?.alphaViewOut()
                if (l2?.visibility != View.VISIBLE) {
                    drinkLayout?.alphaViewIn()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(view, parent)
    }

    override fun getItemCount(): Int {
        return alcoholTracks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alcoholTrack: AlcoholTrack = alcoholTracks[position]
        holder.bind(alcoholTrack, listener)
    }
}