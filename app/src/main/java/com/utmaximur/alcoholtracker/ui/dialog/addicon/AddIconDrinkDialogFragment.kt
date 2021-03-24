package com.utmaximur.alcoholtracker.ui.dialog.addicon


import android.app.AlertDialog.Builder
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.dagger.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.dagger.factory.AddIconDrinkViewModelFactory
import com.utmaximur.alcoholtracker.ui.dialog.addicon.adapter.SelectIconDrinkAdapter
import com.utmaximur.alcoholtracker.ui.dialog.addicon.adapter.SelectIconDrinkAdapter.*


class AddIconDrinkDialogFragment : DialogFragment(), SelectIconListener {

    interface SelectIconListener {
        fun selectIcon(icon: Int)
    }

    private var selectIconListener: SelectIconListener? = null

    fun setListener(selectIconListener: SelectIconListener) {
        this.selectIconListener = selectIconListener
    }

    private lateinit var viewModel: AddIconDrinkViewModel

    private lateinit var iconList: RecyclerView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_add_icon_drink, null)
        builder.setView(view)
        injectDagger()
        initViewModel()
        initUi(view)
        return builder.create()
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initViewModel() {
        val dependencyFactory: AlcoholTrackComponent =
            (requireActivity().application as App).alcoholTrackComponent
        val iconRepository = dependencyFactory.provideIconRepository()
        val viewModel: AddIconDrinkViewModel by viewModels {
            AddIconDrinkViewModelFactory(iconRepository)
        }
        this.viewModel = viewModel
    }

    private fun findViewById(view: View) {
        iconList = view.findViewById(R.id.select_icon_drink_list)
    }

    private fun initUi(view: View) {
        findViewById(view)

        iconList.layoutManager = GridLayoutManager(context, 5)
            iconList.adapter = SelectIconDrinkAdapter()
            (iconList.adapter as SelectIconDrinkAdapter).setIcons(viewModel.getIcons())
            (iconList.adapter as SelectIconDrinkAdapter).setListener(this)
    }

    override fun selectIcon(icon: Int) {
        selectIconListener?.selectIcon(icon)
        dismiss()
    }
}