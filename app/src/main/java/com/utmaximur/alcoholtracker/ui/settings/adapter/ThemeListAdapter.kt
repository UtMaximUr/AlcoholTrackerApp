package com.utmaximur.alcoholtracker.ui.settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.util.toVisible

class ThemeListAdapter(private val themeList: List<String>, private val themeId: Int)  :
    RecyclerView.Adapter<ThemeListAdapter.ViewHolder>() {

    interface ThemeListener {
        fun saveTheme(theme: Int)
    }

    private lateinit var listener: ThemeListener

    fun setListener(listener: ThemeListener){
        this.listener = listener
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_theme, parent, false)) {
        private var themeText: TextView? = null
        private var themeChoice: ImageView? = null

        init {
            themeText = itemView.findViewById(R.id.item_theme_text)
            themeChoice = itemView.findViewById(R.id.item_theme_choice)
        }

        fun bind(theme: String, themeId: Int, position: Int, listener: ThemeListener) {
            themeText?.text = theme
            themeText?.setOnClickListener {
                listener.saveTheme(position)
            }
            if(themeId == position){
                themeChoice?.toVisible()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(view, parent)
    }

    override fun getItemCount(): Int {
        return themeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val theme: String = themeList[position]
        holder.bind(theme, themeId, position, listener)
    }
}