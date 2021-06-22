package com.utmaximur.alcoholtracker.ui.settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.databinding.ItemThemeBinding
import com.utmaximur.alcoholtracker.util.toVisible

class ThemeListAdapter(
    private val onClick: (Int) -> Unit,
    private val themeList: List<String>,
    private val themeId: Int
) :
    RecyclerView.Adapter<ThemeListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemThemeBinding, val onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theme: String, themeId: Int, position: Int) {
            binding.itemThemeText.text = theme
            binding.itemThemeText.setOnClickListener {
                onClick(position)
            }
            if (themeId == position) {
                binding.itemThemeChoice.toVisible()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemThemeBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int {
        return themeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theme: String = themeList[position]
        holder.bind(theme, themeId, position)
    }
}