package com.utmaximur.alcoholtracker.ui.calendar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.ui.dialog.delete.DeleteDialogFragment
import com.utmaximur.alcoholtracker.util.*


class DrinksListAdapter(
    private val alcoholTracks: MutableList<AlcoholTrack>,
    private val listener: OnDrinkAdapterListener,
    private val supportFragmentManager: FragmentManager
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
        private var eventButton: ImageButton? = null
        private var editButton: ImageButton? = null
        private var deleteButton: ImageButton? = null
        private var drinkLayout: LinearLayout? = null
        private var eventLayout: LinearLayout? = null

        init {
            drinkText = itemView.findViewById(R.id.item_drink_text)
            volumeText = itemView.findViewById(R.id.item_volume_text)
            degreeText = itemView.findViewById(R.id.item_degree_text)
            priceText = itemView.findViewById(R.id.item_price_text)
            eventButton = itemView.findViewById(R.id.event_button)
            editButton = itemView.findViewById(R.id.edit_button)
            eventText = itemView.findViewById(R.id.event_text)
            deleteButton = itemView.findViewById(R.id.delete_button)
            drinkLayout = itemView.findViewById(R.id.drink_linearLayout)
            eventLayout = itemView.findViewById(R.id.event_linearLayout)
        }

        fun bind(
            alcoholTrack: AlcoholTrack,
            position: Int,
            listener: OnDrinkAdapterListener,
            supportFragmentManager: FragmentManager,
            onClick: (Int) -> Unit
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
                onClick(position)
                val deleteFragment = DeleteDialogFragment {
                    listener.onDelete(alcoholTrack)
                    onClick(position)
                }
                deleteFragment.show(supportFragmentManager, deleteFragment.tag)
            }

            if (alcoholTrack.event.isEmpty()) {
                eventButton?.toGone()
            }

            eventText?.text = alcoholTrack.event

            eventButton?.setOnClickListener {
                if (drinkLayout?.visibility == View.VISIBLE) {
                    drinkLayout?.toInvisible()
                    eventLayout?.toVisible()
                    drinkLayout?.alphaViewOut()
                    eventLayout?.alphaViewIn()
                    eventButton?.setImageResource(R.drawable.ic_drink_24dp)
                } else {
                    drinkLayout?.toVisible()
                    eventLayout?.toInvisible()
                    drinkLayout?.alphaViewIn()
                    eventLayout?.alphaViewOut()
                    eventButton?.setImageResource(R.drawable.ic_event_24dp)
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
        holder.bind(alcoholTrack, position, listener, supportFragmentManager) {
            alcoholTracks.removeAt(it)
            notifyItemRemoved(it)
            notifyItemRangeChanged(it, itemCount)
        }
    }
}