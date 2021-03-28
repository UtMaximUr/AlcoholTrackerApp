package com.utmaximur.alcoholtracker.ui.calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.ui.dialog.delete.DeleteDialogFragment
import com.utmaximur.alcoholtracker.util.formatVolume
import com.utmaximur.alcoholtracker.util.getResString

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

    interface OnDeleteDyList {
        fun removeAt(position: Int)
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_drink, parent, false)) {
        private var drinkText: TextView? = null
        private var volumeText: TextView? = null
        private var degreeText: TextView? = null
        private var priceText: TextView? = null
        private var editButton: ImageButton? = null
        private var deleteButton: ImageButton? = null

        init {
            drinkText = itemView.findViewById(R.id.item_drink_text)
            volumeText = itemView.findViewById(R.id.item_volume_text)
            degreeText = itemView.findViewById(R.id.item_degree_text)
            priceText = itemView.findViewById(R.id.item_price_text)
            editButton = itemView.findViewById(R.id.edit_button)
            deleteButton = itemView.findViewById(R.id.delete_button)
        }

        fun bind(
            alcoholTrack: AlcoholTrack,
            position: Int,
            listener: OnDrinkAdapterListener,
            onDeleteDyList: OnDeleteDyList,
            supportFragmentManager: FragmentManager
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
                val deleteFragment = DeleteDialogFragment()
                deleteFragment.setListener(object : DeleteDialogFragment.DeleteDialogListener {
                    override fun deleteDrink() {
                        listener.onDelete(alcoholTrack)
                        onDeleteDyList.removeAt(position)
                    }
                })
                deleteFragment.show(supportFragmentManager, "deleteDialog")
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
        holder.bind(alcoholTrack, position, listener, object : OnDeleteDyList {
            override fun removeAt(position: Int) {
                alcoholTracks.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
            }
        }, supportFragmentManager)
    }
}